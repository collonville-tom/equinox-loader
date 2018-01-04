package org.tc.osgi.bundle.utils.conf.gen;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.tc.osgi.bundle.utils.conf.UtilsObjectFactoryImpl;
import org.tc.osgi.bundle.utils.conf.UtilsPropertyFile;
import org.tc.osgi.bundle.utils.conf.jaxb.ConfigurationLibrary;

/**
 * GenConfFile.java.
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_025
 */
public final class GenConfFile {

    /**
     * generateXML.
     * @param lib ConfigurationLibrary
     * @throws JAXBException
     */
    public static void generateXML(final ConfigurationLibrary lib) throws JAXBException {
        final JAXBContext jc = JAXBContext.newInstance(UtilsPropertyFile.getInstance().getJaxbContext());
        final Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(lib, new File("src/main/resources/utils.xml"));

    }

    /**
     * main.
     * @param args String[]
     * @throws JAXBException
     */
    public static void main(final String[] args) throws JAXBException {

        final UtilsObjectFactoryImpl factory = new UtilsObjectFactoryImpl();

        final ConfigurationLibrary lib = factory.createConfigurationLibrary();
        lib.getConfigFiles().add(factory.createElementConfiguration("0.0.1", "String", "version", "Activator"));
        lib.getConfigFiles().add(factory.createElementConfiguration("0.0.1", "String", "version", "XMLPropertyFileTest"));

        GenConfFile.generateXML(lib);

    }

    /**
     * GenConfFile constructor.
     */
    private GenConfFile() {

    }

}
