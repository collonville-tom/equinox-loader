package org.tc.osgi.equinox.loader.cmd.context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.context.utils.BundleFilterUtils;
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

	private BundleFilterUtils filter = new BundleFilterUtils();

	/**
	 * LoadDefaultBundleCmd constructor.
	 *
	 * @param context BundleContext
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

		LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug("####Integration des bundles standard");
		final List<File> listofFiles = collectJarFiles();
		LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug("####Integration des bundles en conflict lors d'un redemarrage");
		listofFiles.addAll(collectJarFiles());
		LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug("####Installing bundles into bundle context");
		this.installingBundles(listofFiles);

	}

	private void installingBundles(List<File> listofFiles) throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
		// Peut etre pas utile
		// final Collection<File> files = filter.filterFile2Install(listofFiles,
		// this.context.getBundles());
		for (final File f : listofFiles) {
			new InstallBundleCmd(this.context, f.getAbsolutePath()).execute();
		}
	}

	private List<File> collectJarFiles() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
		final File bundleDirectory = new File(EquinoxPropertyFile.getInstance().getBundleDirectory());
		final List<File> listofFiles = new ArrayList<File>();
		for (final File f : bundleDirectory.listFiles()) {
			LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug("check file " + f.getName());
			if (filter.isJar(f)) {
				LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug(f.getName() + " est un jar file, ajout pour traitement");
				listofFiles.add(f);
			}
		}
		return listofFiles;
	}

	private List<File> addConflictJarFiles(File bundleDirectory) throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
		final File conflicBundleDirectory = new File(EquinoxPropertyFile.getInstance().getBundleDirectory() + "/conflic");
		final List<File> listofFiles = new ArrayList<File>();
		if (conflicBundleDirectory.exists()) {

			for (final File f : conflicBundleDirectory.listFiles()) {
				if (filter.isJar(f)) {
					LoggerGestionnary.getInstance(LoadDefaultBundleCmd.class).debug("ajout du fichier " + f.getName());
					listofFiles.add(f);
				}
			}
		}
		return listofFiles;
	}

}
