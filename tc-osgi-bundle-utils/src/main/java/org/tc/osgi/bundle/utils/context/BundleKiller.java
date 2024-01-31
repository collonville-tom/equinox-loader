package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

/**
 * BundleKiller.java.
 * 
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_140
 */
public class BundleKiller implements IBundleCommand {

	/**
	 * stopBundle.
	 * 
	 * @param context    BundleContext
	 * @param bundleName String
	 * @throws BundleException
	 */
	public void stopBundle(final BundleContext context, final String bundleName,String bundleVersion) throws BundleException {
		final Bundle[] bundles = context.getBundles();
		for (int i = 0; i < bundles.length; i++) {
			if (bundles[i].getSymbolicName().equals(bundleName)) {
				if (bundles[i].getHeaders().get(VERSION_H).equals(bundleVersion)) {
					bundles[i].stop();
				}
			}
		}
	}

	@Override
	public void processOnBundle(BundleContext context, String bundleName,String version) throws TcOsgiException {
		try {
			this.stopBundle(context, bundleName,version);
		} catch (BundleException e) {
			throw new TcOsgiException("Erreur lors de l'arret du bundle " + bundleName, e);
		}
	}
}
