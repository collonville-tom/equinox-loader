/**
 */
package org.tc.osgi.equinox.loader.cmd;

import org.junit.Test;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

import junit.framework.Assert;

/**
 * ImportBundleCmdTest.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class ImportBundleCmdTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final ImportBundleCmd cmd = new ImportBundleCmd("toto");
        try {
            cmd.execute();
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

}
