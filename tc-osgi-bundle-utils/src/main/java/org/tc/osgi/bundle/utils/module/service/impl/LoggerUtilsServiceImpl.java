package org.tc.osgi.bundle.utils.module.service.impl;

import org.apache.log4j.Logger;
import org.tc.osgi.bundle.utils.interf.logger.ILoggerGestionnary;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class LoggerUtilsServiceImpl implements ILoggerUtilsService {

	private static LoggerUtilsServiceImpl instance = null;

	public static LoggerUtilsServiceImpl getInstance() {
		if (LoggerUtilsServiceImpl.instance == null) {
			LoggerUtilsServiceImpl.instance = new LoggerUtilsServiceImpl();
		}
		return LoggerUtilsServiceImpl.instance;
	}

	/**
	 * @param _class
	 *            Class
	 * @return Logger
	 *
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getLogger(java.lang.Class)
	 */
	@Override
	public Logger getLogger(final Class _class) {
		return LoggerGestionnary.getInstance(_class);
	}

	/**
	 * @return LoggerGestionnary
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getLoggerGestionnary()
	 */
	@Override
	public ILoggerGestionnary getLoggerGestionnary() {
		return LoggerGestionnary.getInstance();
	}

}
