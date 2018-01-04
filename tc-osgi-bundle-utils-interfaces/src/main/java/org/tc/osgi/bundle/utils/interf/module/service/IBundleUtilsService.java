package org.tc.osgi.bundle.utils.interf.module.service;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface IBundleUtilsService {

	/**
	 * getBundleContext.
	 * @return BundleContext
	 * @throws FieldTrackingAssignementException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 */
	public BundleContext getBundleContext() throws FieldTrackingAssignementException, MalformedURLException, RemoteException, NotBoundException,
		NumberFormatException, UnknownHostException;

	/**
	 * getBundleKiller.
	 * @return BundleKiller
	 */
	public IBundleCommand getBundleKiller();

	/**
	 * getBundleStarter.
	 * @return BundleStarter
	 */
	public IBundleCommand getBundleStarter();

	public void getClassloaderContent(final ClassLoader loader);

	public <T> void registerService(Class<T> _class, T instance, final BundleContext context, BundleActivator activator);

	public void unregister(Class _class, BundleActivator activator);

}
