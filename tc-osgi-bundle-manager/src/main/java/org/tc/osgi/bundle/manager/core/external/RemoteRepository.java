package org.tc.osgi.bundle.manager.core.external;

import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.RepoParser;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;



public class RemoteRepository extends AbstractRepository {

	
	
	public RemoteRepository(String name,String url)
	{
		super(name,url);
	}
	
	
	
	public void pull(String bundle,String version)
	{
		//TODO
	}

	public void fetch() {
		try {
			Downloader d=new Downloader();
			LoggerGestionnary.getInstance(RemoteRepository.class).debug("Downloading repofile on "+this.getRepositoryUrl());
			String file=d.getRepoList(this);
			RepoParser parseur=new RepoParser();
			this.setBundles(parseur.parseRepoList(file));
			LoggerGestionnary.getInstance(RemoteRepository.class).debug("Parsing repofile done :"+file);
		} catch (Exception e) {
			LoggerGestionnary.getInstance(RemoteRepository.class).error("Fetching repository "+this.getRepositoryName()+" in error");
		}	
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("[");
		b.append(this.getRepositoryName()).append(",").append("url").append("]\n");
		for(TarGzBundle bundle:this.getBundles())
		{
			b.append(bundle.toString()).append("\n");
		}
		return b.toString();
	}
	
	
}
