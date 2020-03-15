/**
 *
 */
package org.tc.osgi.bundle.utils.io;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.io.StringBufferOutputStream;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * StringBufferOutputStreamTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_150
 * @req STD_BUNDLE_UTILS_150
 */
public class StringBufferOutputStreamTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final StringBufferOutputStream s = new StringBufferOutputStream();
        try {
            s.write(42);
            s.write(42);
            s.write(42);
            s.write(42);
            assertEquals("****", s.toString());
        } catch (final IOException e) {
            fail(e.getMessage());
        }

    }

}
