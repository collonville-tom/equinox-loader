package org.tc.osgi.bundle.manager.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tc.osgi.bundle.manager.core.tools.Downloader;
import org.tc.osgi.bundle.manager.core.tools.RepoParser;
import org.tc.osgi.bundle.manager.exception.ManagerException;
import org.tc.osgi.bundle.utils.interf.collection.element.Pair;

public class Repository {

	private String name;
	private String url;
	private List<TarGzBundle> bundles;
	
	public Repository(String name,String url)
	{
		this.name=name;
		this.url=url;
	}
	
	
	public void pull(String bundle)
	{
		//TODO
	}

	public void fetch() {
		
		try {
			Downloader d=new Downloader();
			String file=d.getRepoList(this.url);
			
			RepoParser parseur=new RepoParser();
			bundles=parseur.parseRepoList(file);

		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("[");
		b.append(name).append(",").append("url").append("]");
		for(TarGzBundle bundle:bundles)
		{
			b.append(bundle.toString()).append("\n");
		}
		return b.toString();
	}
	
	
}
