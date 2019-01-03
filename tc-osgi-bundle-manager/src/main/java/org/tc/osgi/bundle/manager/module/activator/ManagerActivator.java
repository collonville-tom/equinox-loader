package org.tc.osgi.bundle.manager.module.activator;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.jmx.EquinoxLoaderManager;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.manager.rest.SparkRestManager;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;
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
 * 
 * http://localhost:4567/bundles/short
 * http://localhost:4567/fetchRemoteRepo
 * http://localhost:4567/updateLocal
 * http://localhost:4567/pullOnRemoteRepo/tc-osgi-bundle-berkeley-db-wrapper/5.0.73
 * 
 * 
 * 
 */
public class ManagerActivator extends AbstractTcOsgiActivator {

	private TcOsgiProxy<ILoggerUtilsService> iLoggerUtilsService;
	private TcOsgiProxy<IPropertyUtilsService> iPropertyUtilsService;
	private TcOsgiProxy<IBundleUtilsService> iBundleUtilsService;
//	private SparkRestManager sparkRestManager;
//	private String restPort;
	private EquinoxLoaderManager managerBean;
	
	
	
//	public String getRestPort() throws FieldTrackingAssignementException {
//		if (restPort == null) {
//			this.iPropertyUtilsService.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile())
//					.fieldTraking(this, "restPort");
//		}
//		return restPort;
//	}
	

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
		this.iBundleUtilsService = new TcOsgiProxy<IBundleUtilsService>(context, IBundleUtilsService.class);
		BundleUtilsServiceProxy.getInstance().setService(this.iBundleUtilsService.getInstance());
	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {
		this.iBundleUtilsService.close();
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
		this.managerBean=new EquinoxLoaderManager();
		
		
		
		
//		this.sparkRestManager=new SparkRestManager(Integer.parseInt(this.getRestPort()),context);
		
	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
	}

}
