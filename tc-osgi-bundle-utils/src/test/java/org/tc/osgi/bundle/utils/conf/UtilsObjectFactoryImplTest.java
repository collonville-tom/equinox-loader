/**
 */
package org.tc.osgi.bundle.utils.conf;

import org.junit.Test;
import org.tc.osgi.bundle.utils.conf.jaxb.ElementConfiguration;

import junit.framework.Assert;

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
        Assert.assertEquals("defaultValue", el.getDefaultValue());
        Assert.assertEquals("java.lang.String", el.getValueType());
        Assert.assertEquals("value", el.getFieldName());
        Assert.assertEquals("org.tc.osgi.bundle.utils.conf.UtilsObjectFactoryImplTest", el.getClassName());

        Assert.assertEquals(true, el.checkClass(this));
        try {
            Assert.assertEquals(true, el.checkType(this));
        } catch (NoSuchFieldException | SecurityException e) {
            Assert.fail(e.getMessage());
        }
    }

}
