package org.tc.osgi.bundle.manager.mbean;

import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;
import org.tc.osgi.bundle.manager.tools.RepoParser;




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
			LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class).debug("Downloading repofile on "+this.getRepositoryUrl());
			String file=d.getRepoList(this);
			RepoParser parseur=new RepoParser();
			this.setBundles(parseur.parseRepoList(file));
			LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class).debug("Parsing repofile done :"+file);
		} catch (Exception e) {
			LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class).error("Fetching repository "+this.getRepositoryName()+" in error");
		}	
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("[");
		b.append(this.getRepositoryName()).append(",").append("url").append("]\n");
		for(ITarGzBundle bundle:this.getBundles())
		{
			b.append(bundle.toString()).append("\n");
		}
		return b.toString();
	}
	
	
}
