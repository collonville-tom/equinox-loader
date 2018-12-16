package org.tc.osgi.equinox.loader.cmd.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.context.BundleInstaller;
import org.tc.osgi.bundle.utils.context.utils.BundleFilterUtils;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;

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
	 * @param context BundleContext
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
			BundleFilterUtils filter = new BundleFilterUtils();
			final Collection<File> file = filter.filterFile2Install(files, context.getBundles());
			for (final File f : file) {
				if (filter.isJar(f)) {
					final String path = EquinoxPropertyFile.getInstance().getBundleLocalBase() + f.getAbsolutePath();
					LoggerGestionnary.getInstance(this.getClass()).debug("install:" + path);
					new BundleInstaller().processOnBundle(context, path, "unuse");
				}
			}
		} catch (TcOsgiException e) {
			LoggerGestionnary.getInstance(InstallBundleCmd.class)
					.warn("Un bundle a rencontré une erreur lors de son installation", e);
		}

	}
}
