package org.tc.osgi.bundle.manager.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.core.repository.archive.ITarGzArchive;



public abstract class AbstractRepository implements RepositoryMBean{

	private String repositoryName;
	private String repositoryUrl;
	
	private List<ITarGzArchive> bundles=new ArrayList<>();
	
	protected AbstractRepository(String name,String url)
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


	public List<ITarGzArchive> getBundles() {
		return bundles;
	}


	public void setBundles(List<ITarGzArchive> bundles) {
		this.bundles = bundles;
	}
	
	
	
}
