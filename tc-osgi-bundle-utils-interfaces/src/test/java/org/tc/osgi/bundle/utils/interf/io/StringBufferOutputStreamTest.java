/**
 */
package org.tc.osgi.bundle.utils.interf.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

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
			Assert.assertEquals("****", s.toString());
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
		}

	}

}
