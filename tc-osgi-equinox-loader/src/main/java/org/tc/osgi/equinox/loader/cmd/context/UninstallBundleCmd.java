package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
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
            final Bundle[] bundles = context.getBundles();
            for (final Bundle bundle : bundles) {
                if (bundle.getSymbolicName().startsWith(this.bundle)) {
                    LoggerGestionnary.getInstance(UninstallBundleCmd.class).debug("Uninstall bundle:" + bundle.getSymbolicName());
                    bundle.uninstall();
                }
            }
        } catch (final BundleException e) {
            throw new EquinoxCmdException("Uninstall error", e);
        }

    }

}
