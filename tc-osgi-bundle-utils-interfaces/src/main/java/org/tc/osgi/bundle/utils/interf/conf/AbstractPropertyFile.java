package org.tc.osgi.bundle.utils.interf.conf;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * AbstractPropertyFile.java.
 * 
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_025
 * @track SDD_BUNDLE_UTILS_020
 */
public abstract class AbstractPropertyFile {

	/**
	 * String conf_directory.
	 */
	private String conf_directory;

	/**
	 * ResourceBundle resourceBundle.
	 */
	private ResourceBundle resourceBundle = null;

	/**
	 * String version.
	 */
	private String version = null;

	/**
	 * AbstractPropertyFile constructor.
	 * 
	 * @param confFile String
	 * @param loader   ClassLoader
	 */
	protected AbstractPropertyFile(final String confFile, final ClassLoader loader) {
		final File f = new File(".");
		final String tmp = f.getAbsolutePath();
		resourceBundle = ResourceBundle.getBundle(confFile, Locale.getDefault(), loader);
	}

	/**
	 * getBundleRacine.
	 * 
	 * @return String
	 */
	public abstract String getBundleRacine();

	/**
	 * getConfFile.
	 * 
	 * @return String
	 */
	public abstract String getConfFile();

	/**
	 * getConfigDirectory.
	 * 
	 * @return String
	 */
	public String getConfigDirectory() {
		if (conf_directory == null) {
			conf_directory = resourceBundle.getString(getBundleRacine() + "conf_directory");
		}
		return conf_directory;
	}

	/**
	 * getResourceBundle.
	 * 
	 * @return ResourceBundle
	 */
	protected ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * getVersion.
	 * 
	 * @return String
	 */
	public String getVersion() {
		if (version == null) {
			version = resourceBundle.getString(getBundleRacine() + "version");
		}
		return version;
	}

	public abstract String getYamlFile();
}
