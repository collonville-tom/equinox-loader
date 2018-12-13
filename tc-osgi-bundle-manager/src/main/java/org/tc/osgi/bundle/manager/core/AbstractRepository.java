package org.tc.osgi.bundle.manager.core;

import java.util.List;

import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;

public abstract class AbstractRepository {

	private String repositoryName;
	private String repositoryUrl;
	
	private List<TarGzBundle> bundles;
	
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


	public List<TarGzBundle> getBundles() {
		return bundles;
	}


	public void setBundles(List<TarGzBundle> bundles) {
		this.bundles = bundles;
	}
	
	
	public abstract void fetch();
	public abstract void pull(String bundle,String version);
}
