package org.tc.osgi.bundle.utils.tools;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ToolsTest.java.
 *
 * @author collonville thomas
 * @version 0.0.5
 * @req STD_BUNDLE_UTILS_050
 * @track SRS_BUNDLE_UTILS_070
 * @track SDD_BUNDLE_UTILS_090
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
        assertEquals("1,2,3,4,5", UtilsServiceImpl.getInstance().list2String(l, ","));

        final Integer[] tab = {1, 2, 3, 4, 5, 6};
        assertEquals("1,2,3,4,5,6", UtilsServiceImpl.getInstance().tab2String(tab, ","));

    }

}
