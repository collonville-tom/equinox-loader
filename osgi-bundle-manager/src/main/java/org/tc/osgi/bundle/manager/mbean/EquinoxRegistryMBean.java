package org.tc.osgi.bundle.manager.mbean;

import java.io.Serializable;
import java.rmi.Remote;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

public interface EquinoxRegistryMBean extends Remote, Serializable{

	
	public String buildPath(String bundleName, String version)	throws FieldTrackingAssignementException;
	public String bundleShortList() throws TcOsgiException ;
	
	public String bundleDependencies(String bundleName, String version) ;

	public String bundleInfo(String bundleName, String version) throws TcOsgiException ;
	public String bundleService(String bundleName) throws TcOsgiException ;
	
	public String bundleServices() throws TcOsgiException ;
	
	public String bundleInstall(String bundleName, String version) ;

	public String bundleStop(String bundleName, String version);

	public String bundleUninstall(String bundleName, String version) ;

	public String bundleStart(String bundleName, String version);
	

	public String bundleList() throws TcOsgiException ;
}
