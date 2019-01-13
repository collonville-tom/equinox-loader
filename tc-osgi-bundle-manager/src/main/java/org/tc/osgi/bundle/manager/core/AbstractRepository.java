package org.tc.osgi.bundle.manager.core;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.mbean.RepositoryMBean;

public abstract class AbstractRepository implements RepositoryMBean{

	private String repositoryName;
	private String repositoryUrl;
	
	private List<ITarGzBundle> bundles=new ArrayList<>();
	
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


	public List<ITarGzBundle> getBundles() {
		return bundles;
	}


	public void setBundles(List<ITarGzBundle> bundles) {
		this.bundles = bundles;
	}
	
	
	public abstract void fetch();
	public abstract void pull(String bundle,String version);
}
