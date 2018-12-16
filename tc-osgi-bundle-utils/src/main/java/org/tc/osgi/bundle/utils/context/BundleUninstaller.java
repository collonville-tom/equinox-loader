package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class BundleUninstaller implements IBundleCommand {

	@Override
	public void processOnBundle(BundleContext context, String bundleName, String bundleVersion) throws TcOsgiException {
		final Bundle[] bundles = context.getBundles();
		for (final Bundle bundle : bundles) {
			if (bundle.getSymbolicName().startsWith(bundleName)) {
				if (bundle.getHeaders().get(VERSION_H).equals(bundleVersion)) {
					LoggerGestionnary.getInstance(BundleUninstaller.class)
							.debug("Uninstall bundle:" + bundle.getSymbolicName());
					try {
						bundle.uninstall();
					} catch (BundleException e) {

						throw new TcOsgiException("Desinstallation bundle en erreur:" + bundle.getSymbolicName(), e);
					}
				}
			}
		}

	}

}
