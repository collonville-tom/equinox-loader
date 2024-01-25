package org.tc.osgi.equinox.loader.conf;

import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.equinox.loader.cmd.CheckBundleDirectoryConfigCmd;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * EquinoxConfiguration.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 */
public final class EquinoxPropertyFile extends AbstractPropertyFile {

    public final static String BUNDLE_RACINE = "tc.osgi.equinox-loader.";

    /**
     * DefaultConfig conf.
     */
    private static EquinoxPropertyFile conf = null;
    public static String EQUINOX_LOADER_FILE = "equinox-loader";

    /**
     * getInstance.
     *
     * @return DefaultConfig
     * @throws EquinoxConfigException
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     */
    public static EquinoxPropertyFile getInstance() throws EquinoxConfigException, FieldTrackingAssignementException, EquinoxCmdException {
        if (EquinoxPropertyFile.conf == null) {
            EquinoxPropertyFile.conf = new EquinoxPropertyFile();
        }
        return EquinoxPropertyFile.conf;
    }

    /**
     * String baseDirectory.
     */
    private final String bundleDirectory = null;

    /**
     * String bundleLocalBase.
     */
    private final String bundleLocalBase = null;

    /**
     * DefaultConfig constructor.
     *
     * @throws EquinoxConfigException
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     */
    private EquinoxPropertyFile() throws EquinoxConfigException, FieldTrackingAssignementException, EquinoxCmdException {
        super(EquinoxPropertyFile.EQUINOX_LOADER_FILE, EquinoxPropertyFile.class.getClassLoader());
        final CheckBundleDirectoryConfigCmd cmd = new CheckBundleDirectoryConfigCmd(getBundleDirectory());
        cmd.execute();
        if (!cmd.getReturnValue()) {
            throw new EquinoxConfigException("Le repertoire des bundles equinox est invalide");
        }
    }

    /**
     * getBundleDirectory.
     *
     * @return String
     * @throws FieldTrackingAssignementException
     */
    public String getBundleDirectory() throws FieldTrackingAssignementException {
        if (bundleDirectory == null) {
            XMLPropertyFile.getInstance(getXMLFile()).fieldTraking(this, "bundleDirectory");
        }
        return bundleDirectory;
    }

    /**
     * getBundleLocalBase.
     *
     * @return String
     * @throws FieldTrackingAssignementException
     */
    public String getBundleLocalBase() throws FieldTrackingAssignementException {
        if (bundleLocalBase == null) {
            XMLPropertyFile.getInstance(getXMLFile()).fieldTraking(this, "bundleLocalBase");
        }
        return bundleLocalBase;
    }

    @Override
    public String getBundleRacine() {
        return EquinoxPropertyFile.BUNDLE_RACINE;
    }

    @Override
    public String getConfFile() {
        return EquinoxPropertyFile.EQUINOX_LOADER_FILE;
    }

    @Override
    public String getXMLFile() {
        return getConfigDirectory() + getConfFile();
    }
}
