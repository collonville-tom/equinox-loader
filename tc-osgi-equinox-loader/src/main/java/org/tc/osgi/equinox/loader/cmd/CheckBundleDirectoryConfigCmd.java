package org.tc.osgi.equinox.loader.cmd;

import java.io.File;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

/**
 * CheckBundleDirectoryConfigCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class CheckBundleDirectoryConfigCmd extends AbstractEquinoxCmd<Boolean> {

    /**
     * String bundleDir.
     */
    private final String bundleDir;

    /**
     * CheckBundleDirectoryConfigCmd constructor.
     *
     * @param bundleDir
     *            String
     */
    public CheckBundleDirectoryConfigCmd(final String bundleDir) {
        this.bundleDir = bundleDir;
    }

    /**
     * checkConfig.
     *
     * @return boolean
     * @throws FieldTrackingAssignementException
     */
    private boolean checkBundleDirectoryConfig() throws FieldTrackingAssignementException {
        final File bundDir = new File(bundleDir);
        if (!bundDir.exists()) {
            return bundDir.mkdir();
        }
        if (!bundDir.isDirectory()) {
            return false;
        }
        return true;
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            returnValue = checkBundleDirectoryConfig();
        } catch (final FieldTrackingAssignementException e) {
            throw new EquinoxCmdException("Erreur Cmd de verif", e);
        }

    }

}
