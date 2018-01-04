package org.tc.osgi.bundle.utils.module.utils;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class TcOsgiServiceFactory<T> implements ServiceFactory {

	/**
	* int usageCounter.
	*/
	private int usageCounter = 0;

	private T instance;

	public TcOsgiServiceFactory(T instance) {
		super();
		this.instance = instance;
	}

	/**
	 * @param bundle
	 *            Bundle
	 * @param registration
	 *            ServiceRegistration
	 * @return Object
	 * @see org.osgi.framework.ServiceFactory#getService(org.osgi.framework.Bundle,
	 *      org.osgi.framework.ServiceRegistration)
	 */
	@Override
	public Object getService(final Bundle bundle, final ServiceRegistration registration) {
		if (LoggerGestionnary.getInstance(TcOsgiServiceFactory.class) != null)
			LoggerGestionnary.getInstance(TcOsgiServiceFactory.class).debug("Get TCServiceFactory for " + bundle.getSymbolicName());
		usageCounter++;
		if (LoggerGestionnary.getInstance(TcOsgiServiceFactory.class) != null)
			LoggerGestionnary.getInstance(TcOsgiServiceFactory.class).debug("Number of bundles using service " + usageCounter);
		return instance;
	}

	/**
	 * @param bundle
	 *            Bundle
	 * @param registration
	 *            ServiceRegistration
	 * @param service
	 *            Object
	 * @see org.osgi.framework.ServiceFactory#ungetService(org.osgi.framework.Bundle,
	 *      org.osgi.framework.ServiceRegistration, java.lang.Object)
	 */
	@Override
	public void ungetService(final Bundle bundle, final ServiceRegistration registration, final Object service) {
		LoggerGestionnary.getInstance(TcOsgiServiceFactory.class).debug("Release TCServiceFactory for " + bundle.getSymbolicName());
		usageCounter--;
		LoggerGestionnary.getInstance(TcOsgiServiceFactory.class).debug("Number of bundles using service " + usageCounter);
	}
}
