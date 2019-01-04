package org.tc.osgi.bundle.manager.core.external.groovy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.core.internal.LocalRepository;
import org.tc.osgi.bundle.manager.exception.DownloaderException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;

import spark.Response;
import spark.Spark;

// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
public class RemoteRegistry implements RemoteRegistryMBean {

	public static final String OS_PROPERTY = "os.name";
	public static final String WINDOWS = "windows";
	public static final String DEFAULT_NAME = "default";
	public static final String LOCAL_NAME = "local";
	public static final String ARCH_EXT = ".tar.gz";
	public static final String LOCAL_WORK_DIR = ManagerPropertyFile.getInstance().getWorkDirectory() + "/local/";
	public static final String TAR_TAG = ":tar";
	public static final String VERSION_TAG = ":version";
	public static final String TAR_CMD = "tar x";

	private List<RemoteRepository> repositories = new ArrayList<>();
	private LocalRepository localRepository;

	public RemoteRegistry() {
		this.initStaticRepository();
	}

	private void initStaticRepository() {
		RemoteRepository r = new RemoteRepository(DEFAULT_NAME,
				ManagerPropertyFile.getInstance().getStaticRepositoryUrl());
		this.repositories.add(r);
		this.localRepository = new LocalRepository(LOCAL_NAME, ManagerPropertyFile.getInstance().getWorkDirectory());
	}

	public String toString() {
		StringBuilder b = new StringBuilder("Repositories:\n");
		for (RemoteRepository r : repositories) {
			b.append(r.toString()).append("\n");
		}
		return b.toString();
	}

	@Override
	public void buildRegistryCmd() {

		// initialise la connaissance du repo
		Spark.get("/fetchRemoteRepo", (request, response) -> this.fetchRemoteRepoResponse(response));
		// dois constuire le fichier list permettant a d'auutre application de reucperer
		// des tar et de permettre l'installation d'un bundle
		Spark.get("/updateLocal", (request, response) -> this.updateLocalResponse(response));
		// permet de rapatrier dans le repo local un element d'un repo distant
		Spark.get("/pullOnRemoteRepo/:tar/:version", (request, response) -> this.pullOnRemoteRepoResponse(response,
				request.params(TAR_TAG), request.params(VERSION_TAG)));
		// installation d'un bundle du repo local
		Spark.get("/deploy/:tar/:version", (request, response) -> this.installBundleResponse(response,
				request.params(TAR_TAG), request.params(VERSION_TAG)));
		// permet a un autre manager de recuper un tar issu du repo local
		Spark.get("/pullTar/:tar/:version", (request, response) -> this.pushTarResponse(response,
				request.params(TAR_TAG), request.params(VERSION_TAG)));

		// une commande pour avoir l'arbre de dependance? via la compilation des fichier
		// control
		// pendant une installation il faut un mecanisme permettant le download des
		// tar, leur dezip puis l'exploration de dependances
		// l'exploiration des depdnance doit permettre de download des tar suivant si
		// ceux ci ne sont pas deja installé.
		// dans une version ou l'on sera capable de gerer d'autre docker, on fera cette
		// etape dans un autre container avant de switcher dessus si l'install est bonne
	}

	public String installBundleResponse(Response response, String bundleName, String version) {
		return this.installBundle(bundleName, version);

	}

	public String pushTarResponse(Response response, String name, String version) {
		String b=this.pushTar(name, version);
		response.redirect(b.toString());
		return "Redirection to " + b;
	}

	public String pullOnRemoteRepoResponse(Response response, String tarname, String version) {
		response.type("application/json");
		return this.pullOnRemoteRepo(tarname, version);
	}

	private String find(String tarname) throws DownloaderException {
		for (RemoteRepository r : this.repositories) {
			for (TarGzBundle bundle : r.getBundles()) {
				if (bundle.getName().equals(tarname))
					return r.getRepositoryUrl() + "/" + bundle.getUrl();
			}
		}
		throw new DownloaderException(tarname + "not found to download, maybe you bu fetch remote repository");
	}

	public String updateLocalResponse(Response response) {
		response.type("application/json");
		return this.updateLocal();
	}

	public String fetchRemoteRepoResponse(Response response) {
		response.type("application/json");
		return this.fetchRemoteRepo();

	}

	@Override
	public String installBundle(String bundleName, String version) {
		if (!System.getProperty(OS_PROPERTY).toLowerCase().startsWith(WINDOWS)) {
			StringBuilder b = new StringBuilder(TAR_CMD);
			b.append(" -f ").append(LOCAL_WORK_DIR);
			b.append(bundleName).append("-");
			b.append(version).append(ARCH_EXT);
			b.append(" -C /");
			try {
				LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).info("Extractiong" + bundleName);
				LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class)
						.debug("Extractiong CMD" + b.toString());
				Process process = Runtime.getRuntime().exec(b.toString());
				process.waitFor();
				if (process.exitValue() != 0)
					return "Erreur pendant d'extraction de l'archive";
				return new StringBuilder("Extracting ").append(bundleName).append("-").append(version)
						.append(".tar.gz done").toString();
			} catch (InterruptedException | IOException e) {
				LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).error(e);
			}

		}
		return "Os Windows Detected, function not supported";
	}

	@Override
	public String pushTar(String name, String version) {
		for (TarGzBundle tgz : this.localRepository.getBundles()) {
			if (tgz.getName().equals(name) && tgz.getVersion().equals(version)) {
				StringBuilder b = new StringBuilder("/local/");
				b.append(name).append("-").append(version).append(ARCH_EXT);
				return b.toString();
			}
		}
		return "File not found";
	}

	@Override
	public String pullOnRemoteRepo(String tarname, String version) {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class)
				.info("Download targz " + tarname + " into local repo");
		String url = "tarGz not found";
		try {
			url = this.find(tarname);
			Downloader d = new Downloader();
			d.downloadFile(url, new StringBuilder(LOCAL_WORK_DIR).append(tarname).append("-").append(version)
					.append(ARCH_EXT).toString());
		} catch (DownloaderException e) {
			LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).error(e);
		}
		return url;
	}

	@Override
	public String updateLocal() {
		this.localRepository.fetch();
		return new JsonSerialiser().toJson(localRepository);
	}

	@Override
	public String fetchRemoteRepo() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).info("Fetching remote repositories");
		for (RemoteRepository r : repositories) {
			r.fetch();
			LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug(r.toString());
		}
		return new JsonSerialiser().toJson(repositories);
	}

}
