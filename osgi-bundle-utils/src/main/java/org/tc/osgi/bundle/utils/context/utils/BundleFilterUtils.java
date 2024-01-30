package org.tc.osgi.bundle.utils.context.utils;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.Bundle;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;

public class BundleFilterUtils {
	/**
	 * String EXCLUDE_ECLIPSE. Pour eviter un auto load au demarrage
	 */
	public static final String EXCLUDE_ECLIPSE = "osgi";
	public static final String EXCLUDE_ECLIPSE_2 = "org.eclipse.osgi";
	public static final String EXCLUDE_ECLIPSE_3 = "org.apache.felix.framework";

	/**
	 * String INTERFACE_EXT.
	 */
	public static final String INTERFACE_EXT = "interfaces";

	/**
	 * String JAR_EXT.
	 */
	public static final String JAR_EXT = ".jar";

	public Collection<File> filterFile2Install(final List<File> files, final Bundle[] bundles) {
		return CollectionUtilsServiceImpl.getInstance().select(files, new IPredicate<File>() {
			@Override
			public boolean evaluate(final File f) {

				// TODO ce test sur cette extension n'a peut etre pas de sens
//                if (f.getName().contains(BundleFilterUtils.INTERFACE_EXT)) {
//                    LoggerGestionnary.getInstance(this.getClass()).debug("Reject to load (interface file) :" + f.getName());
//                    return false;
//                }
				for (final Bundle b : bundles) {
					if (b.getLocation().contains(f.getAbsolutePath())) {
						LoggerGestionnary.getInstance(this.getClass()).debug("Reject to load (allready install) :" + f.getName());
						return false;
					}
				}

				LoggerGestionnary.getInstance(this.getClass()).debug("Accept to load " + f.getName());
				return true;

			}
		});
	}

	/**
	 * isJar.
	 *
	 * @param file File
	 * @return boolean
	 */
	public boolean isJar(final File file) {
		if (!file.getName().endsWith(BundleFilterUtils.JAR_EXT)) {
			return false;
		}
		/*
		 * if (file.getName().startsWith(BundleFilterUtils.EXCLUDE_ECLIPSE)) { return
		 * false; }
		 */
		if (file.getName().startsWith(BundleFilterUtils.EXCLUDE_ECLIPSE_2)) {
			return false;
		}
		if (file.getName().startsWith(BundleFilterUtils.EXCLUDE_ECLIPSE_3)) {
			return false;
		}
		if (file.isDirectory()) {
			return false;
		}
		return true;
	}
}
