package org.tc.osgi.bundle.manager.core;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.tools.Downloader;

public class ManagerRegistry {

	
	private List<Repository> repositories=new ArrayList<>(); 
	
	public ManagerRegistry()
	{
		
	}
	
	public void initStaticRepository() {
		Repository r=new Repository("default",ManagerPropertyFile.getInstance().getStaticRepositoryUrl());
		r.fetch();
		this.repositories.add(r);
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("Repositories:\n");
		for(Repository r:repositories)
		{
			b.append(r.toString()).append("\n");
		}
		return b.toString();
	}

}
