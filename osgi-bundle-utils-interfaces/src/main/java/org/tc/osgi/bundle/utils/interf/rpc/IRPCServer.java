package org.tc.osgi.bundle.utils.interf.rpc;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AbstractRPCServer.java.
 * @author collonville thomas
 * @version 0.3.0
 * @track SDD_BUNDLE_UTILS_160
 */
public interface IRPCServer {

	/**
	 * addObject.
	 * @param signature String
	 * @param obj Object
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws UnknownHostException
	 * @throws FieldTrackingAssignementException
	 */
	public void addObject(final String signature, final Object obj) throws RemoteException, MalformedURLException, UnknownHostException,
		FieldTrackingAssignementException;

	/**
	 * getRmiAddr.
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getAddr() throws FieldTrackingAssignementException;

	/**
	 * getRmiPort.
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getPort() throws FieldTrackingAssignementException;

}
