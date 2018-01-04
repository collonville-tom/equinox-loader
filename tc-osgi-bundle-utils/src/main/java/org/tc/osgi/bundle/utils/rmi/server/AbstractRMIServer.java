package org.tc.osgi.bundle.utils.rmi.server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rpc.IRPCServer;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * AbstractRMIServer.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_160
 */
public abstract class AbstractRMIServer implements IRPCServer {

	/**
	 * Registry registre.
	 */
	private Registry registre = null;

	/**
	 * @param signature String
	 * @param obj Object
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws UnknownHostException
	 * @throws FieldTrackingAssignementException
	 * @see org.tc.osgi.bundle.utils.rpc.IRPCServer#addObject(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addObject(final String signature, final Object obj) throws RemoteException, MalformedURLException, UnknownHostException,
		FieldTrackingAssignementException {
		final StringBuilder url = new StringBuilder("rmi://").append(InetAddress.getByName(getRmiAddr()).getHostAddress());
		url.append("/").append(signature);
		LoggerGestionnary.getInstance(AbstractRMIServer.class).debug("Enregistrement de l'objet accessible a l'url : " + url);
		if (obj instanceof Remote) {
			registre.rebind(signature, (Remote) obj);
		}
	}

	/**
	 * createRegistry.
	 * @param rmiPort String
	 * @throws RemoteException
	 * @throws FieldTrackingAssignementException
	 */
	protected void createRegistry(final String rmiPort) throws RemoteException, FieldTrackingAssignementException {
		if (registre == null) {
			LoggerGestionnary.getInstance(AbstractRMIServer.class).debug("Le registre RMI n'existant pas on en cree un");
			registre = LocateRegistry.createRegistry(Integer.valueOf(rmiPort));
		}
		LoggerGestionnary.getInstance(AbstractRMIServer.class).debug("Ouverture du port RMI :" + rmiPort);
	}
}
