//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2014.03.26 at 07:32:02 PM CET
//

package org.tc.osgi.bundle.utils.conf.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.tc.osgi.bundle.utils.conf.jaxb
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
/**
 * ObjectFactory.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_025
 * @track SDD_BUNDLE_UTILS_020
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: org.tc.osgi.bundle.utils.conf.jaxb
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConfigurationLibrary }
     *
     */
    public ConfigurationLibrary createConfigurationLibrary() {
        return new ConfigurationLibrary();
    }

    /**
     * Create an instance of {@link ElementConfiguration }
     *
     */
    public ElementConfiguration createElementConfiguration() {
        return new ElementConfiguration();
    }

}
