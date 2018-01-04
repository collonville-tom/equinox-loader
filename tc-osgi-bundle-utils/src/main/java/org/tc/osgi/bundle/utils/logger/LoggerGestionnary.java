package org.tc.osgi.bundle.utils.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.tc.osgi.bundle.utils.conf.UtilsPropertyFile;
import org.tc.osgi.bundle.utils.interf.logger.ILoggerGestionnary;

/**
 * LoggerGestionnary.java.
 *
 * Cette classe evite la declaration en static d'un logger en donnée membre de
 * classe puisqu'elle gere l'enregistrement des logger pour chacune des classes
 *
 * @author Thomas Collonvillé
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_030
 */
public final class LoggerGestionnary implements ILoggerGestionnary {

	/**
	 * LoggerGestionnary log.
	 */
	private static LoggerGestionnary log = null;

	/**
	 * getInstance.
	 * @return LoggerGestionnary
	 */
	public static LoggerGestionnary getInstance() {
		if (LoggerGestionnary.log == null) {
			LoggerGestionnary.log = new LoggerGestionnary();
		}
		return LoggerGestionnary.log;
	}

	/**
	 * getInstance.
	 *
	 * @param _class
	 *            Class
	 * @return Logger
	 */
	public static Logger getInstance(final Class _class) {
		if (LoggerGestionnary.log == null) {
			LoggerGestionnary.log = new LoggerGestionnary();
		}
		if (!LoggerGestionnary.log.getMap().containsKey(_class)) {
			LoggerGestionnary.log.getMap().put(_class, Logger.getLogger(_class));
		}
		return LoggerGestionnary.log.getMap().get(_class);
	}

	/**
	 * String log4jPathfile.
	 */
	private String log4jPathfile = null;

	/**
	 * Map<Class,Logger> map.
	 */
	private final Map<Class, Logger> map = new HashMap<Class, Logger>();

	/**
	 * LoggerGestionnary constructor.
	 */
	private LoggerGestionnary() {
		super();
		DOMConfigurator.configureAndWatch(getLog4jPathfile());

	}

	/**
	 * getLog4jPathfile.
	 * @return String
	 */
	public String getLog4jPathfile() {
		if (log4jPathfile == null) {
			log4jPathfile = UtilsPropertyFile.getInstance().getConfigDirectory() + UtilsPropertyFile.getInstance().getLog4jFile();
		}
		return log4jPathfile;
	}

	/**
	 * getMap.
	 *
	 * @return Map<Class, Logger>
	 */
	private Map<Class, Logger> getMap() {
		return map;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("LoggerGestionnary-LoggerList:\n");
		for (final Map.Entry<Class, Logger> e : map.entrySet()) {
			buff.append(e.getKey().getCanonicalName()).append(",").append(e.getValue().getName()).append("\n");
		}
		return buff.toString();
	}

}
