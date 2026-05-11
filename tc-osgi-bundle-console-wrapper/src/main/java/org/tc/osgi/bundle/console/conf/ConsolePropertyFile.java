package org.tc.osgi.bundle.console.conf;

import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * SpringPropertyFile.java.
 * 
 * @author collonville thomas
 * @version
 * @track
 */
public class ConsolePropertyFile extends AbstractPropertyFile {

	/**
	 * String BUNDLE_RACINE.
	 */
	public final static String BUNDLE_RACINE = "tc.osgi.bundle.console.";

	/**
	 * SpringPropertyFile instance.
	 */
	private static ConsolePropertyFile instance = null;

	/**
	 * String SPRING_FILE.
	 */
	public static final String SPRING_FILE = "console";

	/**
	 * getInstance.
	 * 
	 * @return DefaultConfig
	 * @throws MorphConfigException
	 * @throws FieldTrackingAssignementException
	 */
	public static ConsolePropertyFile getInstance() {
		if (ConsolePropertyFile.instance == null) {
			ConsolePropertyFile.instance = new ConsolePropertyFile();
		}
		return ConsolePropertyFile.instance;
	}

	/**
	 * DefaultConfig constructor.
	 * 
	 * @throws MorphConfigException
	 * @throws FieldTrackingAssignementException
	 */
	private ConsolePropertyFile() {
		super(ConsolePropertyFile.SPRING_FILE, ConsolePropertyFile.class.getClassLoader());
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return ConsolePropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return ConsolePropertyFile.SPRING_FILE;
	}

	@Override
	public String getYamlFile() {
		// TODO Auto-generated method stub
		return ConsolePropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}

}
