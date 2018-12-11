package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.context.BundleUninstaller;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.starter.EquinoxStarter;

/**
 * UninstallBundleCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class UninstallBundleCmd extends AbstractBundleContextCmd {

    /**
     * String bundle.
     */
    private final String bundle;

    /**
     * UninstallBundleCmd constructor.
     *
     * @param context
     *            BundleContext
     * @param bundle
     *            String
     */
    public UninstallBundleCmd(final BundleContext context, final String bundle) {
        super(context);
        this.bundle = bundle;
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            new BundleUninstaller().processOnBundle(this.context,this.bundle);
        } catch (final TcOsgiException e) {
        	LoggerGestionnary.getInstance(UninstallBundleCmd.class).error(
                    "Desinstallation du bundle echoué :" + this.bundle , e);
        }

    }

}
