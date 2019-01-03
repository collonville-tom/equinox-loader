package org.tc.osgi.bundle.manager.core.internal;

public interface EquinoxRegistryMBean {

	
	public String bundleShortList();
	public String bundleDependencies(String bundleName, String version);
	public String bundleInfo(String bundleName, String version) ;
	public String bundleService(String bundleName) ;
	public String bundleServices() ;
    public String bundleInstall(String bundleName, String version) ;
    public String bundleStop(String bundleName, String version) ;
    public String bundleUninstall(String bundleName, String version) ;
    public String bundleStart(String bundleName, String version);
    public String bundleList();
    
    public void buildRegistryCmd();
}
