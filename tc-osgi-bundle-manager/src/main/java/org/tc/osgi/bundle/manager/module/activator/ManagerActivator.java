package org.tc.osgi.bundle.manager.module.activator;

import java.rmi.RemoteException;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.mbean.EquinoxRegistry;
import org.tc.osgi.bundle.manager.mbean.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.mbean.RemoteRegistry;
import org.tc.osgi.bundle.manager.mbean.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.manager.rmi.EquinoxLoaderManager;
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
	private EquinoxLoaderManager manager;
	
	private RemoteRegistry repoRegistry;
	private EquinoxRegistry equinoxRegistry;
	
	private String groovyDependencyBundleName;
	private String groovyDependencyBundleVersion;

	

	

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
		this.manager.unRegister(this.equinoxRegistry,RemoteRegistryMBean.class);
		this.manager.unRegister(this.repoRegistry,EquinoxRegistryMBean.class);
	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		this.manager=new EquinoxLoaderManager();
		this.repoRegistry=new RemoteRegistry();
		this.equinoxRegistry=new EquinoxRegistry();
		try {
			this.manager.createRegistry(this.manager.getPort());
			this.manager.register(repoRegistry,RemoteRegistryMBean.class);
			this.manager.register(equinoxRegistry,EquinoxRegistryMBean.class);
			
			this.iBundleUtilsService.getInstance().getBundleStarter().processOnBundle(context, this.getGroovyDependencyBundleName(),this.getGroovyDependencyBundleVersion());
		} catch (RemoteException e) {
			throw new TcOsgiException("Erreur d'initialisation du bundle manager",e);
		}
	}
	
	public String getGroovyDependencyBundleName() throws FieldTrackingAssignementException  {
        if (this.groovyDependencyBundleName == null) {
        	PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "groovyDependencyBundleName");
        }
        return this.groovyDependencyBundleName;
    }
    
    public String getGroovyDependencyBundleVersion() throws FieldTrackingAssignementException  {
        if (this.groovyDependencyBundleVersion == null) {
        	PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "groovyDependencyBundleVersion");
        }
        return this.groovyDependencyBundleVersion;
    }

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
	}

}
