package org.tc.osgi.equinox.loader.conf;

import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

import junit.framework.Assert;

/**
 * EquinoxConfigurationTest.java.
 *
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_EQUINOX_LOADER_010
 * @track SRS_EQUINOX_LOADER_010
 * @track SDD_EQUINOX_LOADER_010
 */
public class EquinoxConfigurationTest {

    /**
     * testGetBundleLocalBase.
     */
    @Test
    public void testGetBundleLocalBase() {
        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            Assert.assertEquals("file://localhost://", EquinoxPropertyFile.getInstance().getBundleLocalBase());
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxConfigException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * testGetInstance.
     */
    @Test
    public void testGetInstance() {
        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            Assert.assertNotNull(EquinoxPropertyFile.getInstance());
        } catch (final EquinoxConfigException e) {
            Assert.fail(e.getMessage());
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getMessage());
        }
    }

}
