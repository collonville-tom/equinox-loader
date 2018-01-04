package org.tc.osgi.equinox.loader.cmd.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * InstallBundleCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class InstallBundleCmd extends AbstractBundleContextCmd {

    /**
     * String bundlePath.
     */
    private final String bundlePath;

    /**
     * InstallBundleCmd constructor.
     *
     * @param context
     *            BundleContext
     */
    public InstallBundleCmd(final BundleContext context, final String bundlePath) {
        super(context);
        this.bundlePath = bundlePath;
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            final List<File> files = new ArrayList<File>();
            files.add(new File(bundlePath));
            final Collection<File> file = filterFile2Install(files, context.getBundles());
            for (final File f : file) {
                if (isJar(f)) {
                    final String path = EquinoxPropertyFile.getInstance().getBundleLocalBase() + f.getAbsolutePath();
                    LoggerGestionnary.getInstance(this.getClass()).debug("install:" + path);
                    context.installBundle(path);
                }
            }
        } catch (final BundleException e) {
            throw new EquinoxCmdException("Erreur install", e);
        } catch (final FieldTrackingAssignementException e) {
            throw new EquinoxCmdException("Erreur install", e);
        } catch (final EquinoxConfigException e) {
            throw new EquinoxCmdException("Erreur install", e);
        }

    }
}
