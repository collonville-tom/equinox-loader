/**
 */
package org.tc.osgi.equinox.loader.rmi;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rmi.EquinoxLoaderBundleContextImpl;
import org.tc.osgi.bundle.utils.interf.rmi.IEquinoxLoaderBundleContext;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;

/**
 * EquinoxLoaderRMIServerTest.java.
 * @author collonville thomas
 * @version 0.1.5
 * @track
 */
public class EquinoxLoaderRMIServerTest {

    /**
     * test.
     */
    @Test
    public void test() {
        EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
        try {
            EquinoxLoaderRMIServer.getInstance().addObject(IEquinoxLoaderBundleContext.class.getSimpleName(), new EquinoxLoaderBundleContextImpl());
        } catch (RemoteException | MalformedURLException | UnknownHostException | FieldTrackingAssignementException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

}
