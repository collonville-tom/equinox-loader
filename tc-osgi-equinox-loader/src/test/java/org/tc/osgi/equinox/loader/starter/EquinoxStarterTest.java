package org.tc.osgi.equinox.loader.starter;

import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.equinox.loader.cmd.context.LoadDefaultBundleCmd;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

import junit.framework.Assert;

/**
 * EquinoxStarterTest.java.
 *
 * @author Collonville Thomas
 * @version 0.0.2
 * @req STD_EQUINOX_LOADER_020
 * @track SRS_EQUINOX_LOADER_020, SRS_EQUINOX_LOADER_030
 * @track SDD_EQUINOX_LOADER_020, SDD_EQUINOX_LOADER_030
 */
public class EquinoxStarterTest {

    /**
     * testCheck.
     */
    @Test
    public void testCheck() {
        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            EquinoxStarter.getInstance().setConfiguration(".");
            EquinoxStarter.getInstance().compileParameters();
            EquinoxStarter.getInstance().start();
            Assert.assertEquals(true, EquinoxStarter.getInstance().check());
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final Exception e) {
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
            EquinoxStarter.getInstance().setConfiguration(".");
            EquinoxStarter.getInstance().compileParameters();
            Assert.assertNotNull(EquinoxStarter.getInstance());
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxConfigException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testLoadDefaultBundles() {

        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            if (EquinoxStarter.getInstance().check()) {
                EquinoxStarter.getInstance().setConfiguration(".");
                EquinoxStarter.getInstance().compileParameters();
                new LoadDefaultBundleCmd(EquinoxStarter.getInstance().getContext()).execute();
            }
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getMessage());
        } catch (final EquinoxConfigException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * testStart.
     */
    @Test
    public void testStart() {
        try {
            EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
            EquinoxStarter.getInstance().setConfiguration(".");
            EquinoxStarter.getInstance().compileParameters();
            EquinoxStarter.getInstance().start();
        } catch (final FieldTrackingAssignementException e) {
            Assert.fail(e.getMessage());
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
