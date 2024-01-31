package org.tc.osgi.equinox.loader.cmd;

import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

/**
 * ImportBundleCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class ImportBundleCmd extends AbstractEquinoxCmd {

    /**
     * String pathBundle.
     */
    private final String pathBundle;

    /**
     * ImportBundleCmd constructor.
     */
    public ImportBundleCmd(final String path) {
        pathBundle = path;
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        // TODO Cette commande permet d'importer un bundle dans le repo et de le
        // charger
    }

}
