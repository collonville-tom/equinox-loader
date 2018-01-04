package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * BundleStarter.java.
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_125
 * @track SDD_BUNDLE_UTILS_120
 */
public class BundleStarter implements IBundleCommand {

	/**
	 * startBundle.
	 * @param context BundleContext
	 * @param bundleName String
	 * @throws BundleException
	 */

	public void startBundle(final BundleContext context, final String bundleName) throws BundleException {
		final Bundle[] bundles = context.getBundles();

		for (int i = 0; i < bundles.length; i++) {
			if (bundles[i].getSymbolicName().equals(bundleName)) {
				LoggerGestionnary.getInstance(BundleStarter.class).debug("Start of " + bundles[i].getSymbolicName() + ", state:" + bundles[i].getState());
				if (bundles[i].getState() != Bundle.STARTING) { // =8,
					// RESOLVED=4
					bundles[i].start();
				}
			}
		}
	}

	@Override
	public void processOnBundle(BundleContext context, String bundleName) throws TcOsgiException {
		try {
			this.startBundle(context, bundleName);
		} catch (BundleException e) {
			throw new TcOsgiException("Errer lors du lancement du bundle " + bundleName, e);
		}
	}
}