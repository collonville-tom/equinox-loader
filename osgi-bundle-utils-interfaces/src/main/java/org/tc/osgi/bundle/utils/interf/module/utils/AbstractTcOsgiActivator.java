package org.tc.osgi.bundle.utils.interf.module.utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;

public abstract class AbstractTcOsgiActivator implements BundleActivator {

	private TcOsgiProxy<IBundleUtilsService> iBundleUtilsService;

	public TcOsgiProxy<IBundleUtilsService> getIBundleUtilsService() {
		return iBundleUtilsService;
	}

	public void setiBundleUtilsService(TcOsgiProxy<IBundleUtilsService> iBundleUtilsService) {
		this.iBundleUtilsService = iBundleUtilsService;
	}

	protected void initBundleUtilsService(final BundleContext context) throws TcOsgiException {
		this.iBundleUtilsService = new TcOsgiProxy<IBundleUtilsService>(context, IBundleUtilsService.class);

		if (this.iBundleUtilsService.getInstance() == null) {
			this.checkInitBundleUtilsService(context);
		}

	}

	@Override
	public void start(final BundleContext context) throws Exception {
		this.initBundleUtilsService(context);
		this.beforeStart(context);
		initProxys(context);
		initServices(context);
		this.afterStart(context);
	}

	protected void waitSpringBundle(long temp) {
		try {
			Thread.sleep(temp);
		} catch (InterruptedException e) {
			// Nothing to do
		}
	}

	/**
	 * @param context
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		beforeStop(context);
		detachServices(context);
		detachProxys(context);
		iBundleUtilsService.close();
		afterStop(context);
	}

	protected abstract void checkInitBundleUtilsService(final BundleContext context) throws TcOsgiException;

	protected abstract void initProxys(final BundleContext context) throws TcOsgiException;

	protected abstract void initServices(final BundleContext context) throws TcOsgiException;

	protected abstract void detachProxys(final BundleContext context) throws TcOsgiException;

	protected abstract void detachServices(final BundleContext context) throws TcOsgiException;

	protected abstract void beforeStart(final BundleContext context) throws TcOsgiException;

	protected abstract void beforeStop(final BundleContext context) throws TcOsgiException;

	protected abstract void afterStart(final BundleContext context) throws TcOsgiException;

	protected abstract void afterStop(final BundleContext context) throws TcOsgiException;
}
