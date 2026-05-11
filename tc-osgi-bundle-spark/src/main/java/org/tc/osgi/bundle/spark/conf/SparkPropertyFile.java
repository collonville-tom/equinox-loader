package org.tc.osgi.bundle.spark.conf;

import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptConfiguration.java.
 * 
 * @author collonville thomas
 * @version 0.0.1
 */
public final class SparkPropertyFile extends AbstractPropertyFile {
	/**
	 * String EQUINOXLOADERFILE.
	 */
	public static final String SPARK_FILE = "spark";

	/**
	 * String BUNDLE_RACINE.
	 */
	public final static String BUNDLE_RACINE = "tc.osgi.bundle.spark.";

	/**
	 * DefaultConfig conf.
	 */
	private static SparkPropertyFile instance = null;

	private String workDirectory;

	/**
	 * getInstance.
	 * 
	 * @return DefaultConfig
	 * @throws EquinoxConfigException
	 * @throws FieldTrackingAssignementException
	 */
	public static SparkPropertyFile getInstance() {
		if (SparkPropertyFile.instance == null) {
			SparkPropertyFile.instance = new SparkPropertyFile();
		}
		return SparkPropertyFile.instance;
	}

	/**
	 * AptConfiguration constructor.
	 */
	private SparkPropertyFile() {
		super(SparkPropertyFile.SPARK_FILE, SparkPropertyFile.class.getClassLoader());
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return SparkPropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return SparkPropertyFile.SPARK_FILE;
	}

	public String getWorkDirectory() {
		if (workDirectory == null) {
			workDirectory = getResourceBundle().getString(getBundleRacine() + "work_directory");
		}
		return workDirectory;
	}

	@Override
	public String getYamlFile() {
		return SparkPropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}
}
