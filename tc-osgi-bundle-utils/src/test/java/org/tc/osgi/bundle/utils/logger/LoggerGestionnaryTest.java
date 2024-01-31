package org.tc.osgi.bundle.utils.logger;

import org.junit.Test;

/**
 * LoggerGestionnaryTest.java.
 *
 * @req STD_BUNDLE_UTILS_030
 *
 * @track SRS_BUNDLE_UTILS_030
 * @track SDD_BUNDLE_UTILS_030
 * @version 0.0.3
 * @author collonville thomas
 */
public class LoggerGestionnaryTest {

    /**
     * testGetInstance.
     */
    @Test
    public void testGetInstance() {
        LoggerGestionnary.getInstance(LoggerGestionnaryTest.class).debug("LoggerGestionnaryTest");
        LoggerGestionnary.getInstance().toString();
    }
}
