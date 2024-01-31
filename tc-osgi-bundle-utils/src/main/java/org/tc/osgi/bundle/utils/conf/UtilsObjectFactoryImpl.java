package org.tc.osgi.bundle.utils.conf;

import org.tc.osgi.bundle.utils.conf.jaxb.ElementConfiguration;
import org.tc.osgi.bundle.utils.conf.jaxb.ObjectFactory;

/**
 * UtilsObjectFactoryImpl.java.
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_025
 *
 */
public class UtilsObjectFactoryImpl extends ObjectFactory {

    /**
     * createElementConfiguration.
     * @param defaultValue
     * @param valueType String
     * @param fieldName String
     * @param className String
     * @return ElementConfiguration
     */
    public ElementConfiguration createElementConfiguration(final String defaultValue, final String valueType, final String fieldName, final String className) {
        final ElementConfiguration element = new ElementConfiguration();
        element.setClassName(className);
        element.setDefaultValue(defaultValue);
        element.setFieldName(fieldName);
        element.setValueType(valueType);
        return element;
    }

}
