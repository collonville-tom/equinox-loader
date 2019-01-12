package org.tc.osgi.bundle.utils.interf.rmi;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

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
        return EclipseStarter.getSystemBundleContext();//EquinoxStarter.getInstance().getContext();
    }

}
