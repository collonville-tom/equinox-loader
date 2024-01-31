/**
 */
package org.tc.osgi.bundle.utils.rmi;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.rmi.server.AbstractRMIServer;

/**
 * RMITest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_160
 * @track SRS_BUNDLE_UTILS_150
 * @req STD_BUNDLE_UTILS_150
 *
 */
public class RMITest extends AbstractRMIServer implements Serializable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 7618385526881078970L;

    /**
     * RMITest.java.
     */
    public interface Bidon extends Remote, Serializable {
        /**
         * getValue.
         * @return Integer
         */
        Integer getValue();
    }

    /**
     * RMITest.java.
     */
    public class MyBidon implements Bidon {

        /**
         * long serialVersionUID.
         */
        private static final long serialVersionUID = 8360366881654984059L;

        /**
         * Integer value.
         */
        private Integer value;

        /**
         * MyBidon constructor.
         */
        public MyBidon() {

        }

        /**
         * @return Integer
         * @see org.tc.osgi.bundle.utils.rmi.RMITest.Bidon#getValue()
         */
        @Override
        public Integer getValue() {

            return value;
        }

        /**
         * setValue.
         * @param value Integer
         */
        public void setValue(final Integer value) {
            this.value = value;
        }
    }

    /**
     * RMITest constructor.
     */
    public RMITest() {

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
        return "12345";
    }

    /**
     * test.
     */
    @Test
    public void test() {

        try {
            final RMITest testRmi = new RMITest();
            testRmi.createRegistry(getPort());
            final MyBidon b = new MyBidon();
            b.setValue(24);

            testRmi.addObject(Bidon.class.getSimpleName(), b);

            final StringBuilder buff = new StringBuilder("rmi://");
            buff.append(InetAddress.getByName(testRmi.getAddr()).getHostAddress()).append(":").append(testRmi.getPort()).append("/").append(Bidon.class.getSimpleName());
            System.out.println(buff.toString());
            final Remote rem = Naming.lookup(buff.toString());
            if (rem instanceof Bidon) {
                Assert.assertEquals(new Integer(24), ((Bidon) rem).getValue());
            }

        } catch (final RemoteException e) {
            Assert.fail(e.getMessage());
        } catch (final MalformedURLException e) {
            Assert.fail(e.getMessage());
        } catch (final UnknownHostException e) {
            Assert.fail(e.getMessage());
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final NotBoundException e) {
            Assert.fail(e.getMessage());
        }

    }
}
