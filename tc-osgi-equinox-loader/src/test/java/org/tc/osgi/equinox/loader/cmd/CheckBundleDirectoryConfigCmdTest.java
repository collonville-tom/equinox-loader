/**
 */
package org.tc.osgi.equinox.loader.cmd;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

/**
 * CheckBundleDirectoryConfigCmdTest.java.
 * @author collonville thomas
 * @version 0.1.5
 * @track SRS_EQUINOX_LOADER_030
 * @req SDD_EQUINOX_LOADER_030
 */
public class CheckBundleDirectoryConfigCmdTest {

    @Test
    public void test() {
        final CheckBundleDirectoryConfigCmd cmd = new CheckBundleDirectoryConfigCmd("src/test/java");
        try {
            cmd.execute();
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        Assert.assertEquals(true, cmd.getReturnValue());
    }
}
