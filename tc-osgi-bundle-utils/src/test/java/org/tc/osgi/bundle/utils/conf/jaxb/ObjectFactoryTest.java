/**
 *
 */
package org.tc.osgi.bundle.utils.conf.jaxb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ObjectFactoryTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_020
 * @req STD_BUNDLE_UTILS_020
 */
public class ObjectFactoryTest {

    @Test
    public void test() {
        final ObjectFactory factory = new ObjectFactory();
        assertEquals(ConfigurationLibrary.class.getSimpleName(), factory.createConfigurationLibrary().getClass().getSimpleName());
        assertEquals(ElementConfiguration.class.getSimpleName(), factory.createElementConfiguration().getClass().getSimpleName());
    }
}
