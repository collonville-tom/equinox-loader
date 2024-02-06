package org.tc.osgi.bundle.manager.rmi;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.exception.TcManagerMBeanException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rpc.IRPCServer;

public class EquinoxLoaderManager implements IRPCServer {

	private MBeanServer server;

	private String rmiPort;
	private String rmiAddr;

	private Registry registre = null;

	public EquinoxLoaderManager() throws TcManagerMBeanException {
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).info("Construction of MBeanServer");
		this.server = ManagementFactory.getPlatformMBeanServer();
	}

	public void listRMIRegistry() throws AccessException, RemoteException {
		if (this.registre != null) {
			StringBuilder b = new StringBuilder("Liste des services RMI actif").append("\n");
			for (String s : this.registre.list()) {
				b.append(s).append("\n");
			}
			LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug(b.toString());
		}
	}

	private <T> ObjectName buildObjectName(T object) throws TcManagerMBeanException {
		Class<?> c = object.getClass();
		String objectName = c.getPackage().getName() + ":type=" + c.getSimpleName();
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Creation ObjectName: " + objectName);
		try {
			return new ObjectName(objectName);
		} catch (MalformedObjectNameException e) {
			throw new TcManagerMBeanException("Erreur de construction de l'ObjectName " + object.getClass(), e);
		}
	}

	public <T> void unRegister(T object, Class signature) throws TcManagerMBeanException {
		try {
			this.server.unregisterMBean(this.buildObjectName(object));
			this.registre.unbind(signature.getSimpleName());
		} catch (MBeanRegistrationException | InstanceNotFoundException | RemoteException | NotBoundException e) {
			throw new TcManagerMBeanException("Impossible d enlever le bean " + object.getClass(), e);
		}
	}

	public <T> void register(T object, Class signature) throws TcManagerMBeanException {
		try {
			// on l'ajoute au server de bean
			server.registerMBean(object, this.buildObjectName(object));

			// ensuite on l'expose en RMI
			this.addObject(signature.getSimpleName(), object);

		} catch (NotCompliantMBeanException | MBeanRegistrationException | InstanceAlreadyExistsException | TcManagerMBeanException | RemoteException | MalformedURLException
				| UnknownHostException | FieldTrackingAssignementException e) {
			throw new TcManagerMBeanException("Impossible d enregistrer le bean " + object.getClass(), e);
		}
	}

	@Override
	public void addObject(final String signature, final Object obj) throws RemoteException, MalformedURLException, UnknownHostException, FieldTrackingAssignementException {
		final StringBuilder url = new StringBuilder("rmi://").append(InetAddress.getByName(getAddr()).getHostAddress());
		url.append("/").append(signature);
		// LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Enregistrement
		// de l'objet accessible a l'url : " + url);
		if (obj instanceof Remote) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Enregistrement de l'objet accessible a l'url : " + url);
			registre.rebind(signature, (Remote) obj);
		}
		this.listRMIRegistry();

	}

	/**
	 * createRegistry.
	 * 
	 * @param rmiPort String
	 * @throws RemoteException
	 * @throws FieldTrackingAssignementException
	 */
	public void createRegistry(final String rmiPort) throws RemoteException, FieldTrackingAssignementException {
		if (registre == null) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Le registre RMI n'existant pas on en cree un");
			registre = LocateRegistry.createRegistry(Integer.valueOf(rmiPort));
		}
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Ouverture du port RMI :" + rmiPort);
	}

	/**
	 * getRmiAddr.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getAddr() throws FieldTrackingAssignementException {
		if (rmiAddr == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(ManagerPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "rmiAddr");
		}
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Recuperation adresse ecoute RMI :" + rmiAddr);
		return rmiAddr;
	}

	/**
	 * getRmiPort.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getPort() throws FieldTrackingAssignementException {
		if (rmiPort == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(ManagerPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "rmiPort");
		}
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Recuperation port RMI :" + rmiPort);
		return rmiPort;
	}

}
