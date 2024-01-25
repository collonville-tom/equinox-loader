package org.tc.osgi.bundle.manager.mbean;

import java.io.Serializable;
import java.rmi.Remote;

public interface RemoteRegistryMBean extends Remote, Serializable{
	
	public String deployTar(String bundleName, String version);
	public String pushTar(String name, String version);
	public String pullTar(String tarname, String version);
	public String fetchLocalRepo();
	public String fetchRemoteRepo() ;
	

	public String addRepo(String name,String url);
	public String delRepo(String name);
	
}
