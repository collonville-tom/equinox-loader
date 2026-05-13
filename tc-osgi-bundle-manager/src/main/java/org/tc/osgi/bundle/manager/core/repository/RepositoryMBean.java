package org.tc.osgi.bundle.manager.core.repository;

import java.util.List;

import org.tc.osgi.bundle.manager.core.repository.archive.ITarGzArchive;

public interface RepositoryMBean {

	public String getRepositoryName();
	public void setRepositoryName(String repositoryName);
	public String getRepositoryUrl();
	public void setRepositoryUrl(String repositoryUrl);
	
	public List<ITarGzArchive> getBundles();
	public void fetch();
	
}
