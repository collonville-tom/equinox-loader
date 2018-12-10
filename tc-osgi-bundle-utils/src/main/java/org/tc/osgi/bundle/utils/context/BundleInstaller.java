package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

public class BundleInstaller implements IBundleCommand{

	@Override
	public void processOnBundle(BundleContext context, String bundleName) throws TcOsgiException {
		// TODO Auto-generated method stub
		
	}

}
