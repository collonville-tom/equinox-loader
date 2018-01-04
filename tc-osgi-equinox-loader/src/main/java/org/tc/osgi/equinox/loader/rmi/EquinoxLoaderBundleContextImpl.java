package org.tc.osgi.equinox.loader.rmi;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rmi.IEquinoxLoaderBundleContext;
import org.tc.osgi.equinox.loader.starter.EquinoxStarter;

/**
 * EquinoxLoaderBundleContextImpl.java.
 * @author collonville thomas
 * @version 0.1.4
 */
public class EquinoxLoaderBundleContextImpl implements IEquinoxLoaderBundleContext {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -8106942392875227620L;

    /**
     * EquinoxLoaderBundleContextImpl constructor.
     */
    public EquinoxLoaderBundleContextImpl() {

    }

    /**
     * @return BundleContext
     * @throws FieldTrackingAssignementException
     * @see org.tc.osgi.bundle.utils.rmi.IEquinoxLoaderBundleContext#getBundleContext()
     */
    @Override
    public BundleContext getBundleContext() throws FieldTrackingAssignementException {
        return EquinoxStarter.getInstance().getContext();
    }

}
