package org.tc.osgi.bundle.utils.serial;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.collection.element.Pair;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * SerialToolTest.java.
 *
 * @author collonville thomas
 * @version 0.0.5
 * @req STD_BUNDLE_UTILS_040
 * @track SRS_BUNDLE_UTILS_040
 * @track SDD_BUNDLE_UTILS_040
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
            fail();
        }
        try {
            final Pair<String, Integer> clef_valeur2 = serial.read("pair_test.xml", "target/");
            assertEquals(clef_valeur, clef_valeur2);
        } catch (final FileNotFoundException e) {
            fail();
        }

    }

}
