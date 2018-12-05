package org.tc.osgi.bundle.manager.module.activator;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.core.ManagerRegistry;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.interf.module.utils.TcOsgiProxy;



/**
 * Activator.java.
 *
 * @author Collonville Thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_100
 */
public class ManagerActivator extends AbstractTcOsgiActivator {

	private TcOsgiProxy<ILoggerUtilsService> iLoggerUtilsService;
	private TcOsgiProxy<IPropertyUtilsService> iPropertyUtilsService;

	private ManagerRegistry registry=new ManagerRegistry();
	
	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		throw new TcOsgiException("checkInitBundleUtilsService not implemented");
		}

	/**
	 * activeUtilsService.
	 * @param context BundleContext
	 */
	protected void initServices(final BundleContext context) {

	}

	@Override
	protected void initProxys(BundleContext context) throws TcOsgiException {
		this.iPropertyUtilsService = new TcOsgiProxy<IPropertyUtilsService>(context, IPropertyUtilsService.class);
		PropertyServiceProxy.getInstance().setService(this.iPropertyUtilsService.getInstance());
		this.iLoggerUtilsService = new TcOsgiProxy<ILoggerUtilsService>(context, ILoggerUtilsService.class);
		LoggerServiceProxy.getInstance().setService(this.iLoggerUtilsService.getInstance());

	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {
		this.iLoggerUtilsService.close();
		this.iPropertyUtilsService.close();

	}

	@Override
	protected void detachServices(BundleContext context) throws TcOsgiException {


	}

	@Override
	protected void beforeStart(BundleContext context) throws TcOsgiException {

	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {


	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		//main ici on decrit ce que l'on va vouloir faire avec le manager 
		// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant statique et lancer ses elements
		// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant dynamique (autre application equinox) et lancer ses elements
		// avoir un referentiel statique et dynamique cnfiguration via un add
		// avoir un repo local
		registry.initStaticRepository();
		LoggerServiceProxy.getInstance().getLogger(ManagerActivator.class).debug(registry.toString());

		
	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
	}

}
