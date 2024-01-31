package org.tc.osgi.bundle.utils.interf.rmi;

import java.io.Serializable;
import java.rmi.Remote;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * IEquinoxLoaderBundleContext.java.
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_130
 */
public interface IEquinoxLoaderBundleContext extends Remote, Serializable {

	/**
	 * getBundleContext.
	 * @return BundleContext
	 * @throws FieldTrackingAssignementException
	 */
	public BundleContext getBundleContext() throws FieldTrackingAssignementException;

}
