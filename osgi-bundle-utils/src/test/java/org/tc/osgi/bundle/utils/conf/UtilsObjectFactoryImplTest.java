/**
 *
 */
package org.tc.osgi.bundle.utils.conf;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.conf.jaxb.ElementConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * UtilsObjectFactoryImplTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_025
 * @req STD_BUNDLE_UTILS_025
 */
public class UtilsObjectFactoryImplTest {

    /**
     * String value.
     *
     * Champs utilisé pour le test de la methode checkType
     */
    private String value;

    /**
     * test.
     */
    @Test
    public void test() {
        final UtilsObjectFactoryImpl fac = new UtilsObjectFactoryImpl();
        final ElementConfiguration el = fac.createElementConfiguration("defaultValue", String.class.getCanonicalName(), "value", UtilsObjectFactoryImplTest.class.getCanonicalName());
        assertEquals("defaultValue", el.getDefaultValue());
        assertEquals("java.lang.String", el.getValueType());
        assertEquals("value", el.getFieldName());
        assertEquals("org.tc.osgi.bundle.utils.conf.UtilsObjectFactoryImplTest", el.getClassName());

        assertEquals(true, el.checkClass(this));
        try {
            assertEquals(true, el.checkType(this));
        } catch (NoSuchFieldException | SecurityException e) {
            fail(e.getMessage());
        }
    }

}
