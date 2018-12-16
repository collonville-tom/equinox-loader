package org.tc.osgi.bundle.utils.interf.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

public interface IBundleCommand {

	public static final String VERSION_H="Bundle-Version";
	
	public void processOnBundle(final BundleContext context, final String bundleName, String version) throws TcOsgiException;
}
