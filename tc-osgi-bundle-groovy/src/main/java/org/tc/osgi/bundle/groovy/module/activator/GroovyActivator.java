package org.tc.osgi.bundle.groovy.module.activator;

import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.groovy.conf.GroovyPropertyFile;
import org.tc.osgi.bundle.groovy.interf.module.service.IGroovyService;
import org.tc.osgi.bundle.groovy.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.groovy.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.groovy.module.service.impl.GroovyServiceImpl;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.interf.module.utils.TcOsgiProxy;

import groovy.lang.GroovyClassLoader;

/**
 * Activator.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class GroovyActivator extends AbstractTcOsgiActivator {

	private TcOsgiProxy<ILoggerUtilsService> iLoggerUtilsService;
	private TcOsgiProxy<IPropertyUtilsService> iPropertyUtilsService;

	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		throw new TcOsgiException("checkInitBundleUtilsService not implemented");
	}

	@Override
	protected void initProxys(BundleContext context) throws TcOsgiException {
		this.iPropertyUtilsService = new TcOsgiProxy<>(context, IPropertyUtilsService.class);
		PropertyServiceProxy.getInstance().setService(this.iPropertyUtilsService.getInstance());
		this.iLoggerUtilsService = new TcOsgiProxy<>(context, ILoggerUtilsService.class);
		LoggerServiceProxy.getInstance().setService(this.iLoggerUtilsService.getInstance());

	}

	@Override
	protected void initServices(BundleContext context) throws TcOsgiException {
		this.getIBundleUtilsService().getInstance().registerService(IGroovyService.class, GroovyServiceImpl.getInstance(), context, this);
	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {
		this.iLoggerUtilsService.close();
		this.iPropertyUtilsService.close();

	}

	@Override
	protected void detachServices(BundleContext context) throws TcOsgiException {
		this.getIBundleUtilsService().getInstance().unregister(IGroovyService.class, this);

	}

	@Override
	protected void beforeStart(BundleContext context) throws TcOsgiException {
	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {
		((GroovyClassLoader) GroovyServiceImpl.getInstance().getGroovyClassLoader()).clearCache();;
	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		LoggerServiceProxy.getInstance().getLogger(GroovyActivator.class).debug("Chargement des libs groovy par default:"+GroovyPropertyFile.getInstance().getGroovyExtLibs());
		GroovyServiceImpl.getInstance().loadGroovyDirectoryClassLib(GroovyPropertyFile.getInstance().getGroovyExtLibs());
		LoggerServiceProxy.getInstance().getLogger(GroovyActivator.class).debug("Chargement des scripts groovy par default:"+GroovyPropertyFile.getInstance().getGroovyExtScripts());
		GroovyServiceImpl.getInstance().executeGroovyDirectory(GroovyPropertyFile.getInstance().getGroovyExtScripts(),Optional.empty());
	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
	}

}
