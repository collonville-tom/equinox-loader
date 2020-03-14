/**
 *
 */
package org.tc.osgi.bundle.utils.conf;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @req STD_BUNDLE_UTILS_025
 * XMLPropertyFileTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_025
 */
public class XMLPropertyFileTest {

    /**
     * String version.
     */
    private String version;

    /**
     * getVersion.
     * @return String
     * @throws FieldTrackingAssignementException
     */
    public String getVersion() throws FieldTrackingAssignementException {
        if (version == null) {
            XMLPropertyFile.getInstance("src/test/resources/utils").fieldTraking(this, "version");
        }
        return version;
    }

    /**
     * test.
     */
    @Test
    public void test() {
        try {
            assertEquals("0.0.1", getVersion());
        } catch (final FieldTrackingAssignementException e) {
            e.printStackTrace();
            fail();
        }

    }
}
