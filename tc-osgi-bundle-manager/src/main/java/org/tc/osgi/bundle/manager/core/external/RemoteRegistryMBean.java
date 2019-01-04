package org.tc.osgi.bundle.manager.core.external;



public interface RemoteRegistryMBean {
	
	public String installBundle(String bundleName, String version);
	public String pushTar(String name, String version);
	public String pullOnRemoteRepo(String tarname, String version);
	public String updateLocal();
	public String fetchRemoteRepo() ;
	
}
