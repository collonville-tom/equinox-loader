package org.tc.osgi.bundle.manager.mbean;

import java.io.Serializable;
import java.rmi.Remote;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public interface EquinoxRegistryMBean extends Remote, Serializable{

	
	public String buildPath(String bundleName, String version)	throws FieldTrackingAssignementException;
	public String bundleShortList() ;
	
	public String bundleDependencies(String bundleName, String version) ;

	public String bundleInfo(String bundleName, String version) ;
	public String bundleService(String bundleName) ;
	
	public String bundleServices() ;
	
	public String bundleInstall(String bundleName, String version) ;

	public String bundleStop(String bundleName, String version);

	public String bundleUninstall(String bundleName, String version) ;

	public String bundleStart(String bundleName, String version);
	

	public String bundleList() ;
}
