package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.context.BundleUninstaller;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

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
	private final String version;

    /**
     * UninstallBundleCmd constructor.
     *
     * @param context
     *            BundleContext
     * @param bundle
     *            String
     */
    public UninstallBundleCmd(final BundleContext context, final String bundle, final String version) {
        super(context);
        this.bundle = bundle;
        this.version=version;
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            new BundleUninstaller().processOnBundle(this.context,this.bundle,this.version);
        } catch (final TcOsgiException e) {
        	LoggerGestionnary.getInstance(UninstallBundleCmd.class).error(
                    "Desinstallation du bundle echoué :" + this.bundle , e);
        }

    }

}
