package org.tc.osgi.bundle.manager.module.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;

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
	
	public void setService(final IBundleUtilsService service) {
		this.service = service;
	}
	
	@Override
	public BundleContext getBundleContext() throws TcOsgiException {
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

	public IBundleCommand getBundleUninstaller() {
		return this.service.getBundleUninstaller();
	}

	@Override
	public IBundleCommand getBundleInstaller() {
		return this.service.getBundleInstaller();
	}

	
}
