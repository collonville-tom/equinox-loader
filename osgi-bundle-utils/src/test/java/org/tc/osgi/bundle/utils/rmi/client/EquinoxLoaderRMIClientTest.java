/**
 *
 */
package org.tc.osgi.bundle.utils.rmi.client;

import org.junit.jupiter.api.Test;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.rmi.IEquinoxLoaderBundleContext;
import org.tc.osgi.bundle.utils.rmi.server.AbstractRMIServer;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * EquinoxLoaderRMIClientTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_130
 * @req STD_BUNDLE_UTILS_130
 */
public class EquinoxLoaderRMIClientTest extends AbstractRMIServer implements Serializable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -4465000085211650098L;

    /**
     * EquinoxLoaderRMIClientTest constructor.
     * @throws FieldTrackingAssignementException
     * @throws RemoteException
     */
    public EquinoxLoaderRMIClientTest() throws RemoteException, FieldTrackingAssignementException {

    }

    /**
     * @return String
     * @throws FieldTrackingAssignementException
     * @see org.tc.osgi.bundle.utils.rpc.IRPCServer#getAddr()
     */
    @Override
    public String getAddr() throws FieldTrackingAssignementException {
        return "localhost";
    }

    /**
     * @return String
     * @throws FieldTrackingAssignementException
     * @see org.tc.osgi.bundle.utils.rpc.IRPCServer#getPort()
     */
    @Override
    public String getPort() throws FieldTrackingAssignementException {
        return "24689";
    }

    @Test
    public void test() {
        try {

            final EquinoxLoaderRMIClientTest testRmi = new EquinoxLoaderRMIClientTest();
            testRmi.createRegistry(getPort());
            final IEquinoxLoaderBundleContext context = new IEquinoxLoaderBundleContext() {
                private static final long serialVersionUID = -6970760284963376123L;

                @Override
                public BundleContext getBundleContext() throws FieldTrackingAssignementException {
                    return null;
                }
            };

            testRmi.addObject(IEquinoxLoaderBundleContext.class.getSimpleName(), context);

            final EquinoxLoaderRMIClient client = EquinoxLoaderRMIClient.getInstance();

            final IEquinoxLoaderBundleContext iEquinoxLoaderBundleContext = client.getIEquinoxLoaderBundleContext();

            assertEquals(null, iEquinoxLoaderBundleContext.getBundleContext());

        } catch (final NumberFormatException e) {
            fail(e.getMessage());
        } catch (final RemoteException e) {
            fail(e.getMessage());
        } catch (final FieldTrackingAssignementException e) {
            fail(e.getMessage());
        } catch (final UnknownHostException e) {
            fail(e.getMessage());
        } catch (final MalformedURLException e) {
            fail(e.getMessage());
        } catch (TcOsgiException e) {
            fail(e.getMessage());
        }
    }
}
