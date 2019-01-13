package org.tc.osgi.bundle.manager.core;

import java.util.HashMap;
import java.util.Map;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.mbean.LocalRepository;
import org.tc.osgi.bundle.manager.mbean.RemoteRepository;

public class RepositoryManager {

	private Map<String, RemoteRepository> repositories = new HashMap<>();
	private LocalRepository localRepository;
	


	private static RepositoryManager instance;

	public static final String LOCAL_NAME = "local";
	public static final String DEFAULT_NAME = "default";
	
	private RepositoryManager() {
		RemoteRepository r = new RemoteRepository(DEFAULT_NAME,
				ManagerPropertyFile.getInstance().getStaticRepositoryUrl());
		this.repositories.put(DEFAULT_NAME, r);
		this.localRepository = new LocalRepository(LOCAL_NAME, ManagerPropertyFile.getInstance().getWorkDirectory());
	}
	
	public static RepositoryManager getRepositoryManager()
	{
		if(instance==null)
		{
			instance=new RepositoryManager();
		}
		return instance;
	}
	
	public Map<String, RemoteRepository> getRepositories() {
		return repositories;
	}

	public LocalRepository getLocalRepository() {
		return localRepository;
	}
}
