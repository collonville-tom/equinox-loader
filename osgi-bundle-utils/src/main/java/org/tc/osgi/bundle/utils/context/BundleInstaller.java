package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

public class BundleInstaller implements IBundleCommand {

	@Override
	public void processOnBundle(BundleContext context, String path, String version) throws TcOsgiException {
		try {

			LoggerUtilsServiceImpl.getInstance().getLogger(BundleInstaller.class).debug("Installation du bundle: " + path);
			Bundle bundle = context.installBundle(path);
			LoggerUtilsServiceImpl.getInstance().getLogger(BundleInstaller.class)
					.debug(bundle.getSymbolicName() + "[" + bundle.getBundleId() + "] with state " + getBundleState(bundle) + " installed");
		} catch (BundleException e) {
			throw new TcOsgiException("Erreur d'installation " + path, e);
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

}
