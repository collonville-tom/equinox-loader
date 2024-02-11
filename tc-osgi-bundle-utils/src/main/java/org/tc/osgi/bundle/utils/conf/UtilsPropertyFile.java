package org.tc.osgi.bundle.utils.conf;

import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;

/**
 * UtilsPropertyFile.java.
 * 
 * @author collonville thomas
 * @version 0.2.0
 * @track SDD_BUNDLE_UTILS_020
 */
public class UtilsPropertyFile extends AbstractPropertyFile {

	/**
	 * String BUNDLE_RACINE.
	 */
	private final static String BUNDLE_RACINE = "tc.osgi.bundle.utils.";

	/**
	 * UtilsPropertyFile instance.
	 */
	private static UtilsPropertyFile instance = null;

	/**
	 * String UTILS_FILE.
	 */
	private final static String UTILS_FILE = "utils";

	/**
	 * getInstance.
	 * 
	 * @return UtilsPropertyFile
	 */
	public static UtilsPropertyFile getInstance() {
		if (UtilsPropertyFile.instance == null) {
			UtilsPropertyFile.instance = new UtilsPropertyFile();
		}
		return UtilsPropertyFile.instance;
	}

	/**
	 * String log4jFile.
	 */
	private String log4jFile = null;

	private String yamlPropertiesExt = null;

	/**
	 * UtilsPropertyFile constructor.
	 */
	private UtilsPropertyFile() {
		super(UtilsPropertyFile.UTILS_FILE, UtilsPropertyFile.class.getClassLoader());
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return UtilsPropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return UtilsPropertyFile.UTILS_FILE;
	}

	/**
	 * getLog4jFile.
	 * 
	 * @return String
	 */
	public String getLog4jFile() {
		if (log4jFile == null) {
			log4jFile = getResourceBundle().getString(getBundleRacine() + "log4j.file");
		}
		return log4jFile;
	}

	@Override
	public String getYamlFile() {
		return UtilsPropertyFile.getInstance().getConfigDirectory() + getConfFile();

	}

	public String getYamlPropertiesExtension() {
		if (yamlPropertiesExt == null) {
			yamlPropertiesExt = getResourceBundle().getString(getBundleRacine() + "yaml_properties_ext");
		}
		return yamlPropertiesExt;
	}

}
