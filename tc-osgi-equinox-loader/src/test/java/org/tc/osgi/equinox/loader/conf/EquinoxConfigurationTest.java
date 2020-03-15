package org.tc.osgi.equinox.loader.conf;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

import static org.junit.jupiter.api.Assertions.*;

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
            assertEquals("file://localhost://", EquinoxPropertyFile.getInstance().getBundleLocalBase());
        } catch (final FieldTrackingAssignementException e) {
            fail(e.getMessage());
        } catch (final EquinoxConfigException e) {
            fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            fail(e.getMessage());
        }
    }

    /**
     * testGetInstance.
     */
    @Test
    public void testGetInstance() {
        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            assertNotNull(EquinoxPropertyFile.getInstance());
        } catch (final EquinoxConfigException e) {
            fail(e.getMessage());
        } catch (final FieldTrackingAssignementException e) {
            fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            fail(e.getMessage());
        }
    }

}
