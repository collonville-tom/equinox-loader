package org.tc.osgi.bundle.utils.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * UtilsPropertyFileTest.java.
 * 
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_020
 * @req STD_BUNDLE_UTILS_020
 */
public class UtilsPropertyFileTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		Assert.assertEquals("./src/test/resources/", UtilsPropertyFile.getInstance().getConfigDirectory());
		Assert.assertEquals("0.0.6", UtilsPropertyFile.getInstance().getVersion());
	}

}
