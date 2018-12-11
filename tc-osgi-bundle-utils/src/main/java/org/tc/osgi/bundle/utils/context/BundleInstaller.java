package org.tc.osgi.bundle.utils.context;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

public class BundleInstaller implements IBundleCommand{

	@Override
	public void processOnBundle(BundleContext context, String path) throws TcOsgiException {
		try {
			context.installBundle(path);
		} catch (BundleException e) {
			throw new TcOsgiException("Erreur d'installation "+path,e);
		}
		
	}

}
