package org.tc.osgi.bundle.manager.core.external;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.core.internal.LocalRepository;
import org.tc.osgi.bundle.manager.exception.DownloaderException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

import spark.Response;
import spark.Spark;

// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
public class RepositoryRegistry extends AbstractRegistry {

	private List<RemoteRepository> repositories = new ArrayList<>();
	private LocalRepository localRepository;

	public RepositoryRegistry() {
		this.initStaticRepository();
	}

	private void initStaticRepository() {
		RemoteRepository r = new RemoteRepository("default",
				ManagerPropertyFile.getInstance().getStaticRepositoryUrl());
		this.repositories.add(r);
		this.localRepository=new LocalRepository("local",ManagerPropertyFile.getInstance().getWorkDirectory());
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
		Spark.get("/fetchRemoteRepo", (request, response) -> this.fetchRemoteRepo(response));
		// dois constuire le fichier list  permettant a d'auutre application de reucperer des tar et de permettre l'installation d'un bundle
		Spark.get("/updateLocal", (request, response) -> this.updateLocal(response));
		// permet de rapatrier dans le repo local un element d'un repo distant
		Spark.get("/pullOnRemoteRepo/:tarname/:version",	(request, response) -> this.pullOnRemoteRepo(response, request.params(":tarname"),request.params(":version")));
		// installation d'un bundle du repo local
		Spark.get("/install/:tarname/:version", (request, response) -> this.installBundle(response, request.params(":tarname")));
		// permet a un autre manager de recuper un tar issu du repo local
		Spark.get("/pullTar/:tarname/:version", (request, response) -> this.pullTar(response, request.params(":tarname")));
	}

	private Object pullTar(Response response, String params) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object installBundle(Response response, String params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Object pullOnRemoteRepo(Response response, String tarname,String version) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Download targz into local repo");
		String url="tarGz not found";
		try {
			url=this.find(tarname);
			Downloader d=new Downloader();
			d.downloadFile(url,ManagerPropertyFile.getInstance().getWorkDirectory()+"/local/"+tarname+"-"+version+".tar.gz");
		}
		catch(DownloaderException e)
		{
			LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).error(e);
		}
		return url;
	}

	private String find(String tarname) throws DownloaderException {
		for(RemoteRepository r: this.repositories)
		{
			for(TarGzBundle bundle:r.getBundles())
			{
				if(bundle.getName().equals(tarname))
					return r.getRepositoryUrl()+"/"+bundle.getUrl();
			}
		}
		throw new DownloaderException(tarname + "not found to download, maybe you bu fetch remote repository");
	}

	private Object updateLocal(Response response) {
		response.type("application/json");
		this.localRepository.fetch();
		return new JsonSerialiser().toJson(localRepository);
	}



	public String fetchRemoteRepo(Response response) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Fetching remote repository");
		for (RemoteRepository r : repositories) {
			r.fetch();
			LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug(r.toString());
		}
		return new JsonSerialiser().toJson(repositories);
	}

}
