package org.tc.osgi.bundle.manager.module.service;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.tc.osgi.bundle.utils.context.BundleKiller;
import org.tc.osgi.bundle.utils.context.BundleStarter;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.utils.TcOsgiServiceFactory;
import org.tc.osgi.bundle.utils.rmi.client.EquinoxLoaderRMIClient;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class BundleUtilsServiceProxy implements IBundleUtilsService {

	
	/**
	 * UtilsServiceProxy instance.
	 */
	private static BundleUtilsServiceProxy instance = null;

	/**
	 * getInstance.
	 * @return UtilsServiceProxy
	 */
	public static BundleUtilsServiceProxy getInstance() {
		if (BundleUtilsServiceProxy.instance == null) {
			BundleUtilsServiceProxy.instance = new BundleUtilsServiceProxy();
		}
		return BundleUtilsServiceProxy.instance;
	}

	/**
	 * IUtilsService service.
	 */
	private IBundleUtilsService service = null;

	/**
	 * UtilsServiceProxy constructor.
	 */
	private BundleUtilsServiceProxy() {

	}
	
	
	
	@Override
	public BundleContext getBundleContext() throws FieldTrackingAssignementException, MalformedURLException,
			RemoteException, NotBoundException, NumberFormatException, UnknownHostException {
		return this.service.getBundleContext();
	}

	@Override
	public IBundleCommand getBundleKiller() {
		return this.service.getBundleKiller();
	}

	@Override
	public IBundleCommand getBundleStarter() {
		return this.service.getBundleStarter();
	}

	@Override
	public void getClassloaderContent(ClassLoader loader) {
		this.service.getClassloaderContent(loader);
	}

	@Override
	public <T> void registerService(Class<T> _class, T instance, BundleContext context, BundleActivator activator) {
		this.service.registerService(_class, instance, context, activator);
		
	}

	@Override
	public void unregister(Class _class, BundleActivator activator) {
		this.service.unregister(_class, activator);
	}

	
}
