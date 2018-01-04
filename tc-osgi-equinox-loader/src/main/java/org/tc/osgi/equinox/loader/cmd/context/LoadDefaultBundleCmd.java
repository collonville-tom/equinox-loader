package org.tc.osgi.equinox.loader.cmd.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * LoadDefaultBundleCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.1.7
 */
public class LoadDefaultBundleCmd extends AbstractBundleContextCmd {

    /**
     * LoadDefaultBundleCmd constructor.
     *
     * @param context
     *            BundleContext
     */
    public LoadDefaultBundleCmd(final BundleContext context) {
        super(context);
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            this.loadDefaultBundles();
        } catch (final FieldTrackingAssignementException e) {
            throw new EquinoxCmdException("Error cmd", e);
        } catch (final EquinoxConfigException e) {
            throw new EquinoxCmdException("Error cmd", e);
        }

    }

    /**
     * loadDefaultBundles.
     *
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     * @throws EquinoxConfigException
     */
    private void loadDefaultBundles() throws FieldTrackingAssignementException, EquinoxCmdException, EquinoxConfigException {
        final File bundleDirectory = new File(EquinoxPropertyFile.getInstance().getBundleDirectory());
        final File conflicBundleDirectory = new File(EquinoxPropertyFile.getInstance().getBundleDirectory() + "/conflic");
        final List<File> listofFiles = new ArrayList<File>();
        for (final File f : bundleDirectory.listFiles()) {
            if (this.isJar(f)) {
                listofFiles.add(f);
            }
        }
        if (conflicBundleDirectory.exists()) {
            for (final File f : conflicBundleDirectory.listFiles()) {
                if (this.isJar(f)) {
                    listofFiles.add(f);
                }
            }
        }
        if (this.context != null) {
            LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).error("Le context bundle n'a pas ete correctement construit");

            final Collection<File> files = this.filterFile2Install(listofFiles, this.context.getBundles());
            for (final File f : files) {
                new InstallBundleCmd(this.context, f.getAbsolutePath()).execute();
            }
        }
    }

}
