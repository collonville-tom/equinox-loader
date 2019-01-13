package org.tc.osgi.bundle.manager.mbean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.RepositoryManager;
import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.exception.DownloaderException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;


// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
public class RemoteRegistry implements RemoteRegistryMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9039919806902291851L;
	public static final String OS_PROPERTY = "os.name";
	public static final String WINDOWS = "windows";
	
	
	public static final String ARCH_EXT = ".tar.gz";
	public static final String LOCAL_WORK_DIR = ManagerPropertyFile.getInstance().getWorkDirectory() + "/local/";
	public static final String TAR_TAG = ":tar";
	public static final String VERSION_TAG = ":version";
	public static final String TAR_CMD = "tar x";
	

	

	public RemoteRegistry() {
		
	}

	

	public String toString() {
		StringBuilder b = new StringBuilder("Repositories:\n");
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			b.append(r.toString()).append("\n");
		}
		return b.toString();
	}

	
	private String find(String tarname) throws DownloaderException {
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			for (ITarGzBundle bundle : r.getBundles()) {
				if (bundle.getName().equals(tarname))
					return r.getRepositoryUrl() + "/" + bundle.getUrl();
			}
		}
		throw new DownloaderException(tarname + "not found to download, maybe you bu fetch remote repository");
	}

	@Override
	public String deployTar(String bundleName, String version) {
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
		for (ITarGzBundle tgz : RepositoryManager.getRepositoryManager().getLocalRepository().getBundles()) {
			if (tgz.getName().equals(name) && tgz.getVersion().equals(version)) {
				StringBuilder b = new StringBuilder("/local/");
				b.append(name).append("-").append(version).append(ARCH_EXT);
				return b.toString();
			}
		}
		return "File not found";
	}
	

	

	@Override
	public String pullTar(String tarname, String version) {
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
	public String fetchLocalRepo() {
		RepositoryManager.getRepositoryManager().getLocalRepository().fetch();
		return new JsonSerialiser().toJson(RepositoryManager.getRepositoryManager().getLocalRepository());
	}

	@Override
	public String fetchRemoteRepo() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).info("Fetching remote repositories");
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			r.fetch();
			LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug(r.toString());
		}
		return new JsonSerialiser().toJson(RepositoryManager.getRepositoryManager().getRepositories());
	}



	@Override
	public String addRepo(String name,String url) {
		if(RepositoryManager.getRepositoryManager().getRepositories().containsKey(name))
			return "Repository allready exist";
		RepositoryManager.getRepositoryManager().getRepositories().put(name, new RemoteRepository(name, url));
		return "Repository "+name+" added";
		
	}

	@Override
	public String removeRepo(String name) {
		if(RepositoryManager.getRepositoryManager().getRepositories().containsKey(name))
			return "Repository does not exist";
		RepositoryManager.getRepositoryManager().getRepositories().remove(name);
		return "Repository "+name+" removed";
	}

}
