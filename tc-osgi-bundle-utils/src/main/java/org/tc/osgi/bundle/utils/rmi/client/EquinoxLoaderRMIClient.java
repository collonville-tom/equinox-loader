package org.tc.osgi.bundle.utils.rmi.client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


import org.tc.osgi.bundle.utils.conf.UtilsPropertyFile;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.rmi.IEquinoxLoaderBundleContext;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * RMIClient.java.
 * 
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_130
 */
public class EquinoxLoaderRMIClient {

	/**
	 * EquinoxLoaderRMIClient instance.
	 */
	private static EquinoxLoaderRMIClient instance = null;

	/**
	 * getInstance.
	 * 
	 * @return EquinoxLoaderRMIClient
	 * @throws RemoteException
	 * @throws FieldTrackingAssignementException
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 */
	public synchronized static EquinoxLoaderRMIClient getInstance() {
		if (EquinoxLoaderRMIClient.instance == null) {
			EquinoxLoaderRMIClient.instance = new EquinoxLoaderRMIClient();
		}
		return EquinoxLoaderRMIClient.instance;
	}

	/**
	 * IEquinoxLoaderBundleContext iContext.
	 */
	private IEquinoxLoaderBundleContext iContext = null;

	/**
	 * String rmiAddr.
	 */
	private String rmiAddr;

	/**
	 * String rmiPort.
	 */
	private String rmiPort;

	/**
	 * EquinoxLoaderRMIClient constructor.
	 * 
	 * @throws RemoteException
	 * @throws FieldTrackingAssignementException
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 */
	private EquinoxLoaderRMIClient() {
	}

	/**
	 * getIEquinoxLoaderBundleContext.
	 * 
	 * @return IEquinoxLoaderBundleContext
	 * @throws TcOsgiException 
	 * @throws FieldTrackingAssignementException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws UnknownHostException
	 */
	public IEquinoxLoaderBundleContext getIEquinoxLoaderBundleContext() throws TcOsgiException {
		try {
			if (iContext == null) {
				final StringBuilder buff = new StringBuilder("rmi://");

				buff.append(InetAddress.getByName(getRmiAddr()).getHostAddress()).append(":").append(getRmiPort())
						.append("/").append(IEquinoxLoaderBundleContext.class.getSimpleName());
					
				LoggerGestionnary.getInstance(EquinoxLoaderRMIClient.class).debug(buff.toString());
				final Remote rem = Naming.lookup(buff.toString());
				LoggerGestionnary.getInstance(EquinoxLoaderRMIClient.class).debug(rem.toString());
				if (rem instanceof IEquinoxLoaderBundleContext) {
					LoggerGestionnary.getInstance(EquinoxLoaderRMIClient.class).debug(
							"Chargement via rmi de l'objet " + IEquinoxLoaderBundleContext.class.getSimpleName());
					iContext = (IEquinoxLoaderBundleContext) rem;
				}
				iContext = (IEquinoxLoaderBundleContext) rem;
			}
			return iContext;
		} catch (UnknownHostException |FieldTrackingAssignementException|MalformedURLException|RemoteException|NotBoundException e) {
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
			XMLPropertyFile.getInstance(UtilsPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiAddr");
		}
		LoggerGestionnary.getInstance(EquinoxLoaderRMIClient.class)
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
			XMLPropertyFile.getInstance(UtilsPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiPort");
		}
		LoggerGestionnary.getInstance(EquinoxLoaderRMIClient.class).debug("Recuperation port RMI :" + rmiPort);
		return rmiPort;
	}
}
