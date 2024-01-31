/**
 */
package org.tc.osgi.bundle.utils.conf.jaxb;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(ConfigurationLibrary.class.getSimpleName(), factory.createConfigurationLibrary().getClass().getSimpleName());
        Assert.assertEquals(ElementConfiguration.class.getSimpleName(), factory.createElementConfiguration().getClass().getSimpleName());
    }
}
