package org.tc.osgi.bundle.manager.core;

import java.util.List;

import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;

public interface RepositoryMBean {

	public String getRepositoryName();
	public void setRepositoryName(String repositoryName);
	public String getRepositoryUrl();
	public void setRepositoryUrl(String repositoryUrl);
	public List<TarGzBundle> getBundles();
	public void fetch();
	public void pull(String bundle,String version);
}
