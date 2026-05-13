package org.tc.osgi.bundle.manager.core.registry;

import java.io.IOException;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.repository.RepositoryManager;
import org.tc.osgi.bundle.manager.core.repository.archive.ITarGzArchive;
import org.tc.osgi.bundle.manager.core.repository.remote.RemoteRepository;
import org.tc.osgi.bundle.manager.exception.DownloaderException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;


// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
//TODO ceci doit correspondre a une interface rest exposant les données du repo local (listing du contenu, les targz recuperable)


public class ArchiveRegistry implements ArchiveRegistryMBean {


	private static final long serialVersionUID = -9039919806902291851L;
	public static final String OS_PROPERTY = "os.name";
	public static final String WINDOWS = "windows";
	
	
	public static final String ARCH_EXT = ".tar.gz";
	public static final String LOCAL_WORK_DIR = ManagerPropertyFile.getInstance().getWorkDirectory() + "/local/";
	public static final String TAR_TAG = ":tar";
	public static final String VERSION_TAG = ":version";
	public static final String TAR_CMD = "tar x";



	public ArchiveRegistry() {
		
	}
	

	

	@Override
	public String fetchLocalRepository() {
		RepositoryManager.getRepositoryManager().getLocalRepository().fetch();
		return new JsonSerialiser().toJson(RepositoryManager.getRepositoryManager().getLocalRepository());
	}

		
	@Override
	public String addRepository(String name,String url) {
		if(RepositoryManager.getRepositoryManager().getRepositories().containsKey(name))
			return "Repository allready exist";
		RepositoryManager.getRepositoryManager().getRepositories().put(name, new RemoteRepository(name, url));
		return "Repository "+name+" added";
		
	}

	@Override
	public String delRepository(String name) {
		if(!RepositoryManager.getRepositoryManager().getRepositories().containsKey(name))
			return "Repository does not exist";
		RepositoryManager.getRepositoryManager().getRepositories().remove(name);
		return "Repository "+name+" removed";
	}
	
	
	
	@Override
	public String fetchAllRemoteRepository() {
		LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).info("Fetching all remote repositories");
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			r.fetch();
			LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).debug(r.toString());
		}
		return new JsonSerialiser().toJson(RepositoryManager.getRepositoryManager().getRepositories());
	}
	
	
	@Override
	public String fetchRemoteRepository(String name) {
		LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).info("Fetching remote repository");
		if (RepositoryManager.getRepositoryManager().getRepositories().containsKey(name))
		{
			RemoteRepository repository=RepositoryManager.getRepositoryManager().getRepositories().get(name);
			repository.fetch();
			
		}
		return new JsonSerialiser().toJson(RepositoryManager.getRepositoryManager().getRepositories());
	}

	

	@Override
	public String pullTar(String tarname, String version) {
		LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).info("Download targz " + tarname + " into local repo");
		String url = "tarGz not found";
		try {
			url = this.find(tarname);
			Downloader d = new Downloader();
			d.downloadFile(url, new StringBuilder(LOCAL_WORK_DIR).append(tarname).append("-").append(version)
					.append(ARCH_EXT).toString());
		} catch (DownloaderException e) {
			LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).error(e);
		}
		return url;
	}

	
	private String find(String tarname) throws DownloaderException {
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			for (ITarGzArchive bundle : r.getBundles()) {
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
				LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).info("Extractiong" + bundleName);
				LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class)
						.debug("Extractiong CMD" + b.toString());
				Process process = Runtime.getRuntime().exec(b.toString());
				process.waitFor();
				if (process.exitValue() != 0)
					return "Erreur pendant d'extraction de l'archive";
				return new StringBuilder("Extracting ").append(bundleName).append("-").append(version)
						.append(".tar.gz done").toString();
			} catch (InterruptedException | IOException e) {
				LoggerServiceProxy.getInstance().getLogger(ArchiveRegistry.class).error(e);
			}

		}
		return "Os Windows Detected, function not supported";
	}

	


	public String toString() {
		StringBuilder b = new StringBuilder("Repositories:\n");
		for (RemoteRepository r : RepositoryManager.getRepositoryManager().getRepositories().values()) {
			b.append(r.toString()).append("\n");
		}
		return b.toString();
	}

	
}
