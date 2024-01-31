package org.tc.osgi.bundle.utils.tools;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;

import junit.framework.Assert;

/**
 * ToolsTest.java.
 *
 * @req STD_BUNDLE_UTILS_050
 *
 * @track SRS_BUNDLE_UTILS_070
 * @track SDD_BUNDLE_UTILS_090
 * @version 0.0.5
 * @author collonville thomas
 */
public class ToolsTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		final List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		l.add(5);
		Assert.assertEquals("1,2,3,4,5", UtilsServiceImpl.getInstance().list2String(l, ","));

		final Integer[] tab = { 1, 2, 3, 4, 5, 6 };
		Assert.assertEquals("1,2,3,4,5,6", UtilsServiceImpl.getInstance().tab2String(tab, ","));

	}

}
