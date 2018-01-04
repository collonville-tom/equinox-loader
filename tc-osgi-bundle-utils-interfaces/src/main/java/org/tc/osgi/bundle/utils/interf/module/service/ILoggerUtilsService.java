package org.tc.osgi.bundle.utils.interf.module.service;

import org.apache.log4j.Logger;
import org.tc.osgi.bundle.utils.interf.logger.ILoggerGestionnary;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface ILoggerUtilsService {

	/**
	 * getLogger.
	 *
	 * @return Logger
	 */
	public Logger getLogger(Class _class);

	/**
	 * getLoggerGestionnary.
	 * @return LoggerGestionnary
	 */
	public ILoggerGestionnary getLoggerGestionnary();

}
