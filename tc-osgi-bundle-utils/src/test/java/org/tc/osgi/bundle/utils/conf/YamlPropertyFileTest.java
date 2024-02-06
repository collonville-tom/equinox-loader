/**
 */
package org.tc.osgi.bundle.utils.conf;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * @req STD_BUNDLE_UTILS_025 XMLPropertyFileTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_025
 */
public class YamlPropertyFileTest {

	/**
	 * String version.
	 */
	private String version;

	/**
	 * getVersion.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getVersion() throws FieldTrackingAssignementException {
		if (version == null) {
			YamlPropertyFile.getInstance("src/test/resources/utils").fieldTraking(this, "version");
		}
		return version;
	}

	/**
	 * test.
	 */
	@Test
	public void test() {
		try {
			Assert.assertEquals("0.0.1", getVersion());
		} catch (final FieldTrackingAssignementException e) {
			e.printStackTrace();
			Assert.fail();
		}

	}
}
