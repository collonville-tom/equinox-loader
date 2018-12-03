package org.tc.osgi.bundle.manager.conf;

import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;

/**
 * UtilsPropertyFile.java.
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_020
 */
public class ManagerPropertyFile extends AbstractPropertyFile {

    /**
     * String BUNDLE_RACINE.
     */
    private final static String BUNDLE_RACINE = "tc.osgi.bundle.manager.";

    /**
     * UtilsPropertyFile instance.
     */
    private static ManagerPropertyFile instance = null;

    /**
     * String UTILS_FILE.
     */
    private final static String UTILS_FILE = "utils";

    /**
     * getInstance.
     * @return UtilsPropertyFile
     */
    public static ManagerPropertyFile getInstance() {
        if (ManagerPropertyFile.instance == null) {
            ManagerPropertyFile.instance = new ManagerPropertyFile();
        }
        return ManagerPropertyFile.instance;
    }

    /**
     * String jaxbContext.
     */
    private String jaxbContext = null;

    /**
     * String log4jFile.
     */
    private String log4jFile = null;

    /**
     * String xmlPropertiesExt.
     */
    private String xmlPropertiesExt = null;

    /**
     * UtilsPropertyFile constructor.
     */
    private ManagerPropertyFile() {
        super(ManagerPropertyFile.UTILS_FILE, ManagerPropertyFile.class.getClassLoader());
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
     */
    @Override
    public String getBundleRacine() {
        return ManagerPropertyFile.BUNDLE_RACINE;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
     */
    @Override
    public String getConfFile() {
        return ManagerPropertyFile.UTILS_FILE;
    }

    /**
     * getJaxbContext.
     * @return String
     */
    public String getJaxbContext() {
        if (jaxbContext == null) {
            jaxbContext = getResourceBundle().getString(getBundleRacine() + "jaxb_context");
        }
        return jaxbContext;
    }

    /**
     * getLog4jFile.
     * @return String
     */
    public String getLog4jFile() {
        if (log4jFile == null) {
            log4jFile = getResourceBundle().getString(getBundleRacine() + "log4j.file");
        }
        return log4jFile;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getXMLFile()
     */
    @Override
    public String getXMLFile() {
        return ManagerPropertyFile.getInstance().getConfigDirectory() + getConfFile();

    }

    /**
     * getXmlPropertiesExtension.
     * @return String
     */
    public String getXmlPropertiesExtension() {
        if (xmlPropertiesExt == null) {
            xmlPropertiesExt = getResourceBundle().getString(getBundleRacine() + "xml_properties_ext");
        }
        return xmlPropertiesExt;
    }

}
