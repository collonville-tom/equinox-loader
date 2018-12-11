package org.tc.osgi.bundle.manager.core.external;

import java.util.List;
import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.exception.ManagerException;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.RepoParser;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;



public class RemoteRepository extends AbstractRepository {

	
	private String repositoryName;
	private String repositoryUrl;
	
	private List<TarGzBundle> bundles;
	
	public RemoteRepository(String name,String url)
	{
		this.repositoryName=name;
		this.repositoryUrl=url;
	}
	
	public String getRepositoryName() {
		return repositoryName;
	}



	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}



	public String getRepositoryUrl() {
		return repositoryUrl;
	}



	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}


	public List<TarGzBundle> getBundles() {
		return bundles;
	}


	public void setBundles(List<TarGzBundle> bundles) {
		this.bundles = bundles;
	}
	
	public void pull(String bundle)
	{
		//TODO
	}

	public void fetch() {
		
		try {
			Downloader d=new Downloader();
			String file=d.getRepoList(this.repositoryUrl);
			LoggerGestionnary.getInstance(RemoteRepository.class).debug("Downloading repofile ");
			
			RepoParser parseur=new RepoParser();
			bundles=parseur.parseRepoList(file);
			LoggerGestionnary.getInstance(RemoteRepository.class).debug("Parsing repofile done :"+file);
		} catch (ManagerException e) {
			LoggerGestionnary.getInstance(RemoteRepository.class).error("Fetching repository "+this.repositoryName+" in error");
		}	
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("[");
		b.append(repositoryName).append(",").append("url").append("]\n");
		for(TarGzBundle bundle:bundles)
		{
			b.append(bundle.toString()).append("\n");
		}
		return b.toString();
	}
	
	
}
