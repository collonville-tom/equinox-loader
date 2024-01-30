package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

/**
 * BundleStarter.java.
 * 
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_125
 * @track SDD_BUNDLE_UTILS_120
 */
public class BundleStarter implements IBundleCommand {

	/**
	 * startBundle.
	 * 
	 * @param context    BundleContext
	 * @param bundleName String
	 * @throws BundleException
	 */

	public void startBundle(final BundleContext context, final String bundleName, String bundleVersion) throws BundleException {
		final Bundle[] bundles = context.getBundles();
		LoggerGestionnary.getInstance(BundleStarter.class).debug("Recherche du bundle " + bundleName + bundleVersion);
		for (int i = 0; i < bundles.length; i++) {
			LoggerUtilsServiceImpl.getInstance().getLogger(BundleStarter.class)
					.debug(bundles[i].getSymbolicName() + "[" + bundles[i].getBundleId() + "] with state " + getBundleState(bundles[i]) + " installed");
			if (bundles[i].getSymbolicName().equals(bundleName)) {
				LoggerGestionnary.getInstance(BundleStarter.class).debug("Header " + bundles[i].getHeaders().get(VERSION_H) + " and it is: " + bundleVersion);
				if (bundles[i].getHeaders().get(VERSION_H).equals(bundleVersion)) {
					LoggerGestionnary.getInstance(BundleStarter.class).debug("Starting of " + bundles[i].getSymbolicName() + ", state:" + bundles[i].getState());
					if (bundles[i].getState() != Bundle.STARTING) { // =8,
						// RESOLVED=4
						bundles[i].start();
						LoggerUtilsServiceImpl.getInstance().getLogger(BundleStarter.class)
								.debug(bundles[i].getSymbolicName() + "[" + bundles[i].getBundleId() + "] with state " + getBundleState(bundles[i]) + " installed");
					}
				}
			}
		}
	}

	public static String getBundleState(Bundle bundle) {
		switch (bundle.getState()) {
		case 0x1:
			return "UNINSTALLED";
		case 0x2:
			return "INSTALLED";
		case 0x4:
			return "RESOLVED";
		case 0x8:
			return "STARTING";
		case 0x10:
			return "STOPPING";
		case 0x20:
			return "ACTIVE";
		default:
			throw new IllegalArgumentException("Unexpected value: " + bundle.getState());
		}
	}

	@Override
	public void processOnBundle(BundleContext context, String bundleName, String version) throws TcOsgiException {
		try {
			this.startBundle(context, bundleName, version);
		} catch (BundleException e) {
			throw new TcOsgiException("Erreur lors du lancement du bundle " + bundleName, e);
		}
	}
}