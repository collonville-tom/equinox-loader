package org.tc.osgi.bundle.manager.jmx.interf.registry;

public interface EquinoxRegistryMBean {

	
	String bundleShortList();
	String bundleDependencies(String bundleName, String version);
	String bundleInfo(String bundleName, String version) ;
    String bundleService(String bundleName) ;
    String bundleServices() ;
    String bundleInstall(String bundleName, String version) ;
    String bundleStop(String bundleName, String version) ;
    String bundleUninstall(String bundleName, String version) ;
    String bundleStart(String bundleName, String version);
    String bundleList();
}
