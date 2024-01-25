package org.tc.osgi.bundle.utils.interf.collection.element;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * PairTest.java.
 *
 * @req STD_BUNDLE_UTILS_015
 * @track SRS_BUNDLE_UTILS_015
 * @track SDD_BUNDLE_UTILS_015
 * @version 0.0.5
 * @author collonville thomas
 */
public class PairTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		final Pair<String, Integer> clef_valeur = new Pair<String, Integer>("toto", 2);
		clef_valeur.hashCode();
		clef_valeur.toString();
		assertEquals("toto", clef_valeur.getFirst());
		assertEquals(Integer.valueOf(2), clef_valeur.getSecond());

		final Pair<String, Integer> clef_valeur2 = new Pair<String, Integer>("toto", 2);

		assertEquals(false, clef_valeur.equals(null));
		assertEquals(false, clef_valeur.equals("Toto"));
		assertEquals(true, clef_valeur.equals(clef_valeur2));

		final Pair<String, Integer> clef_valeur3 = new Pair<String, Integer>("tata", 2);
		assertEquals(false, clef_valeur.equals(clef_valeur3));

	}

}
