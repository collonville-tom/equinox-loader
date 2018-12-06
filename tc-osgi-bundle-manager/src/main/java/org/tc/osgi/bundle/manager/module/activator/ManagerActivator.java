package org.tc.osgi.bundle.manager.module.activator;

import java.io.IOException;
import java.net.URI;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.ManagerRegistry;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.manager.rest.IManagerCmdImpl;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.interf.module.utils.TcOsgiProxy;

import spark.Spark;

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
	private IManagerCmdImpl managerCmd;
	private String sparkDependencyBundleName;

	public String getSparkDependencyBundleName() throws FieldTrackingAssignementException {
		if (sparkDependencyBundleName == null) {
			this.iPropertyUtilsService.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile())
					.fieldTraking(this, "sparkDependencyBundleName");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ManagerActivator.class)
				.debug("Lancement auto du bundle :" + sparkDependencyBundleName);
		return sparkDependencyBundleName;
	}

	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		throw new TcOsgiException("checkInitBundleUtilsService not implemented");
	}

	/**
	 * activeUtilsService.
	 * 
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
		this.iLoggerUtilsService.getInstance().getLogger(ManagerActivator.class).debug("lancement du bundle spark");
		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context,
				this.getSparkDependencyBundleName());
	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {

	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		Spark.get("/rest", (req, res) -> "Hello Rest");
	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
	}

}
