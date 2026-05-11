package org.tc.osgi.bundle.groovy.conf;

import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptConfiguration.java.
 * 
 * @author collonville thomas
 * @version 0.0.1
 */
public final class GroovyPropertyFile extends AbstractPropertyFile {
	/**
	 * String EQUINOXLOADERFILE.
	 */
	public static final String GROOVY_FILE = "groovy";

	/**
	 * String BUNDLE_RACINE.
	 */
	public final static String BUNDLE_RACINE = "tc.osgi.bundle.groovy.";

	public String groovy_extensions_libs;
	public String groovy_extensions_scripts;

	public String getGroovyExtLibs() {
		if (groovy_extensions_libs == null) {
			groovy_extensions_libs = getResourceBundle().getString(getBundleRacine() + "groovy_extensions_libs");
		}
		return groovy_extensions_libs;
	}

	public String getGroovyExtScripts() {
		if (groovy_extensions_scripts == null) {
			groovy_extensions_scripts = getResourceBundle().getString(getBundleRacine() + "groovy_extensions_scripts");
		}
		return groovy_extensions_scripts;
	}

	/**
	 * DefaultConfig conf.
	 */
	private static GroovyPropertyFile instance = null;

	/**
	 * getInstance.
	 * 
	 * @return DefaultConfig
	 * @throws EquinoxConfigException
	 * @throws FieldTrackingAssignementException
	 */
	public static GroovyPropertyFile getInstance() {
		if (GroovyPropertyFile.instance == null) {
			GroovyPropertyFile.instance = new GroovyPropertyFile();
		}
		return GroovyPropertyFile.instance;
	}

	/**
	 * AptConfiguration constructor.
	 */
	private GroovyPropertyFile() {
		super(GroovyPropertyFile.GROOVY_FILE, GroovyPropertyFile.class.getClassLoader());
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return GroovyPropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return GroovyPropertyFile.GROOVY_FILE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getXMLFile()
	 */
	@Override
	public String getYamlFile() {
		return GroovyPropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}

}
