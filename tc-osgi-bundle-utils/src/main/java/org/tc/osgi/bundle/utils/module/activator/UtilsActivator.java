package org.tc.osgi.bundle.utils.module.activator;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.conf.UtilsPropertyFile;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.ICollectionUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.ICommandRunnerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.BundleUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.CommandRunnerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.PropertyUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;
import org.tc.osgi.bundle.utils.rmi.client.EquinoxLoaderRMIClient;

/**
 * Activator.java.
 *
 * @author Collonville Thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_100
 */
public class UtilsActivator extends AbstractTcOsgiActivator {

	/**
	 * String version.
	 */
	private String version;

	/**
	 * getVersion.
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getVersion() throws FieldTrackingAssignementException {
		if (version == null) {
			XMLPropertyFile.getInstance(UtilsPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "version");
		}
		return version;
	}

	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		this.getIBundleUtilsService().setProxy(new BundleUtilsServiceImpl());
		this.getIBundleUtilsService().getInstance().registerService(IBundleUtilsService.class, this.getIBundleUtilsService().getInstance(), context, this);
	}

	/**
	 * activeUtilsService.
	 * @param context BundleContext
	 */
	protected void initServices(final BundleContext context) {
		this.getIBundleUtilsService().getInstance().registerService(ILoggerUtilsService.class, new LoggerUtilsServiceImpl(), context, this);
		this.getIBundleUtilsService().getInstance().registerService(IUtilsService.class, new UtilsServiceImpl(), context, this);
		this.getIBundleUtilsService().getInstance().registerService(ICollectionUtilsService.class, new CollectionUtilsServiceImpl(), context, this);
		this.getIBundleUtilsService().getInstance().registerService(IPropertyUtilsService.class, new PropertyUtilsServiceImpl(), context, this);
		this.getIBundleUtilsService().getInstance().registerService(ICommandRunnerUtilsService.class, CommandRunnerUtilsServiceImpl.getInstance(), context,
			this);

	}

	@Override
	protected void initProxys(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachServices(BundleContext context) throws TcOsgiException {
		this.getIBundleUtilsService().getInstance().unregister(ICommandRunnerUtilsService.class, this);
		this.getIBundleUtilsService().getInstance().unregister(IUtilsService.class, this);
		this.getIBundleUtilsService().getInstance().unregister(ICollectionUtilsService.class, this);
		this.getIBundleUtilsService().getInstance().unregister(ILoggerUtilsService.class, this);
		this.getIBundleUtilsService().getInstance().unregister(IPropertyUtilsService.class, this);
		this.getIBundleUtilsService().getInstance().unregister(IBundleUtilsService.class, this);

	}

	@Override
	protected void beforeStart(BundleContext context) throws TcOsgiException {

	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {
		LoggerGestionnary.getInstance(UtilsActivator.class).debug("VersionStatic:" + UtilsPropertyFile.getInstance().getVersion());
		LoggerGestionnary.getInstance(UtilsActivator.class).debug("VersionDynamic:" + this.getVersion());

	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		LoggerGestionnary.getInstance(UtilsActivator.class).debug("UtilsService start");

		try {
			LoggerGestionnary.getInstance(UtilsActivator.class).debug("Test conso objet RMI");
			EquinoxLoaderRMIClient.getInstance().getIEquinoxLoaderBundleContext();
			LoggerGestionnary.getInstance(UtilsActivator.class).debug("Test conso objet RMI OK");
		} catch (final FieldTrackingAssignementException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		} catch (final MalformedURLException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		} catch (final RemoteException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		} catch (final NotBoundException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		} catch (final NumberFormatException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		} catch (final UnknownHostException e) {
			LoggerGestionnary.getInstance(UtilsActivator.class).error(e);
		}

	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
		LoggerGestionnary.getInstance(UtilsActivator.class).debug("UtilsService stop");
	}

}
