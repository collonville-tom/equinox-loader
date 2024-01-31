package org.tc.osgi.bundle.utils.interf.module.utils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * UtilsServiceTracker.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class TcOsgiServiceTracker<T> extends ServiceTracker {

	/**
	 * AptServiceTracker constructor.
	 * @param context BundleContext
	 * @throws InvalidSyntaxException
	 * @throws BundleException
	 */
	public TcOsgiServiceTracker(final BundleContext context, Class<T> _class) throws InvalidSyntaxException, BundleException {
		super(context, _class.getName(), null);

	}

	/**
	 * @param reference ServiceReference
	 * @return Object
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(final ServiceReference reference) {
		System.out.println("Inside TCServiceTracker.addingService " + reference.getBundle());
		return super.addingService(reference);
	}

	/**
	 * getService.
	 * @return T
	 */
	public T getService() {

		return (T) super.getService();
	}

	/**
	 * @param reference ServiceReference
	 * @param service Object
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(final ServiceReference reference, final Object service) {
		System.out.println("Inside TCServiceTracker.removedService " + reference.getBundle());
		super.removedService(reference, service);
	}

}
