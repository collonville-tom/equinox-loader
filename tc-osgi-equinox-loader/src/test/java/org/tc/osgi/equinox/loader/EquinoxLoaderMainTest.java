package org.tc.osgi.equinox.loader;

import org.junit.Test;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;

import junit.framework.Assert;

/**
 * EquinoxLoaderMainTest.java.
 *
 * @author collonville thomas
 * @version 0.0.3
 * @req STD_EQUINOX_LOADER_030
 * @track SRS_EQUINOX_LOADER_040, SRS_EQUINOX_LOADER_050, SRS_EQUINOX_LOADER_060
 * @track SDD_EQUINOX_LOADER_040
 */
public class EquinoxLoaderMainTest {

    /**
     * test.
     */
    @Test
    public void test() {
        EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
        EquinoxLoaderMain.tempo = 2000;
        final String[] defaultArgs = {};
        final Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (final InterruptedException e) {
                    Assert.fail(e.getLocalizedMessage());
                }
                EquinoxLoaderMain.isAlive = false;

            }
        });
        t.start();
        EquinoxLoaderMain.main(defaultArgs);

    }
}
