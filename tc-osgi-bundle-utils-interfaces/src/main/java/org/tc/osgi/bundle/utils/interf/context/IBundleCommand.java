package org.tc.osgi.bundle.utils.interf.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;

public interface IBundleCommand {

	public void processOnBundle(final BundleContext context, final String bundleName) throws TcOsgiException;
}
