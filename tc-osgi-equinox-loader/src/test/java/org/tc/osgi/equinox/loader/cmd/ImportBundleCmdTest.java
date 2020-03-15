/**
 *
 */
package org.tc.osgi.equinox.loader.cmd;

import org.junit.jupiter.api.Test;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

import static org.junit.jupiter.api.Assertions.fail;

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
            fail(e.getLocalizedMessage());
        }
    }

}
