package org.tc.osgi.bundle.manager.rmi;


import java.net.InetAddress;

import java.rmi.Naming;
import java.rmi.Remote;


import javax.management.MalformedObjectNameException;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.mbean.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.mbean.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;





public class ManagerRmiClient {

	private EquinoxRegistryMBean equinoxRegistryMBean;
	private RemoteRegistryMBean remoteRegistryMBean;
	
	private String rmiPort;
	private String rmiAddr;

	private static ManagerRmiClient instance;
	
	public static ManagerRmiClient getInstance() throws MalformedObjectNameException, TcOsgiException
	{
		if(instance==null)
			instance=new ManagerRmiClient();
		return instance;
		
	}
	
	private ManagerRmiClient() throws MalformedObjectNameException, TcOsgiException {
		LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug("Creation du client RMI d'utilisation du manager");
//		this.getRemoteRegistry();
//		this.getEquinoxRegistry();
		
	}

	public RemoteRegistryMBean getRemoteRegistry() throws TcOsgiException {
		try {
			if (remoteRegistryMBean == null) {
				final StringBuilder buff = new StringBuilder("rmi://");

				buff.append(InetAddress.getByName(getRmiAddr()).getHostAddress()).append(":").append(getRmiPort())
						.append("/").append(RemoteRegistryMBean.class.getSimpleName());
				LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(buff.toString());
				final Remote rem = Naming.lookup(buff.toString());
				LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(rem.toString());
				if (rem instanceof RemoteRegistryMBean) {
					LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(
							"Chargement via rmi de l'objet " + EquinoxRegistryMBean.class.getSimpleName());
					remoteRegistryMBean = (RemoteRegistryMBean) rem;
				}
			}
			return remoteRegistryMBean;
		} catch (Throwable  e) {
			LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).error("Erreur critique", e);
			throw new TcOsgiException("Erreur de recuperation du bundleContext",e);
		} 
	}

	public EquinoxRegistryMBean getEquinoxRegistry() throws TcOsgiException {
		try {
			if (equinoxRegistryMBean == null) {
				final StringBuilder buff = new StringBuilder("rmi://");

				buff.append(InetAddress.getByName(getRmiAddr()).getHostAddress()).append(":").append(getRmiPort())
						.append("/").append(EquinoxRegistryMBean.class.getSimpleName());
				LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(buff.toString());
				final Remote rem = Naming.lookup(buff.toString());
				LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(rem.toString());
				if (rem instanceof EquinoxRegistryMBean) {
					LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug(
							"Chargement via rmi de l'objet " + EquinoxRegistryMBean.class.getSimpleName());
					equinoxRegistryMBean = (EquinoxRegistryMBean) rem;
				}
			}
			return equinoxRegistryMBean;
		} catch (Throwable  e) {
			
			throw new TcOsgiException("Erreur de recuperation du bundleContext",e);
		} 
	}
	
	/**
	 * getRmiAddr.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getRmiAddr() throws FieldTrackingAssignementException {
		if (rmiAddr == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiAddr");
		}
		LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class)
				.debug("Recuperation adresse ecoute RMI :" + rmiAddr);
		return rmiAddr;
	}

	/**
	 * getRmiPort.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getRmiPort() throws FieldTrackingAssignementException {
		if (rmiPort == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiPort");
		}
		LoggerServiceProxy.getInstance().getLogger(ManagerRmiClient.class).debug("Recuperation port RMI :" + rmiPort);
		return rmiPort;
	}
	
}
