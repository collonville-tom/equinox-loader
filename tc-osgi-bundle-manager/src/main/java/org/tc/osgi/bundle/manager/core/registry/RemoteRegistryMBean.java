package org.tc.osgi.bundle.manager.core.registry;

import java.io.Serializable;
import java.rmi.Remote;

public interface RemoteRegistryMBean extends Remote, Serializable{
	
	public String fetchLocalRepository();
	
	public String addRepository(String name,String url);
	public String delRepository(String name);
	public String fetchRemoteRepository(String name) ;
	public String fetchAllRemoteRepository() ;
	
	
	public String pullTar(String tarname, String version);
	public String deployTar(String bundleName, String version);
	

	
}
