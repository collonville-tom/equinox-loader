package org.tc.osgi.bundle.utils.serial;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.collection.element.Pair;

import junit.framework.Assert;

/**
 * SerialToolTest.java.
 *
 * @req STD_BUNDLE_UTILS_040
 *
 * @track SRS_BUNDLE_UTILS_040
 * @track SDD_BUNDLE_UTILS_040
 * @version 0.0.5
 * @author collonville thomas
 */
public class SerialToolTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final Pair<String, Integer> clef_valeur = new Pair<String, Integer>("toto", 2);
        final SerialTool<Pair<String, Integer>> serial = new SerialTool<Pair<String, Integer>>();
        try {
            serial.save(clef_valeur, "pair_test.xml", "target/");
        } catch (final FileNotFoundException e) {
            Assert.fail();
        }
        try {
            final Pair<String, Integer> clef_valeur2 = serial.read("pair_test.xml", "target/");
            Assert.assertEquals(clef_valeur, clef_valeur2);
        } catch (final FileNotFoundException e) {
            Assert.fail();
        }

    }

}
