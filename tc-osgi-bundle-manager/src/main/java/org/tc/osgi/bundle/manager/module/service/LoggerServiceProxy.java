package org.tc.osgi.bundle.manager.module.service;

import org.apache.log4j.Logger;
import org.tc.osgi.bundle.utils.interf.logger.ILoggerGestionnary;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;

/**
 * UtilsServiceProxy.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class LoggerServiceProxy implements ILoggerUtilsService {

	/**
	 * UtilsServiceProxy instance.
	 */
	private static LoggerServiceProxy instance = null;

	/**
	 * getInstance.
	 * @return UtilsServiceProxy
	 */
	public static LoggerServiceProxy getInstance() {
		if (LoggerServiceProxy.instance == null) {
			LoggerServiceProxy.instance = new LoggerServiceProxy();
		}
		return LoggerServiceProxy.instance;
	}

	/**
	 * IUtilsService service.
	 */
	private ILoggerUtilsService service = null;

	/**
	 * UtilsServiceProxy constructor.
	 */
	private LoggerServiceProxy() {

	}

	/**
	 * @param _class Class
	 * @return Logger
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getLogger(java.lang.Class)
	 */
	@Override
	public Logger getLogger(final Class _class) {
		return service.getLogger(_class);
	}

	@Override
	public ILoggerGestionnary getLoggerGestionnary() {
		return service.getLoggerGestionnary();
	}

	/**
	 * getService.
	 * @return IUtilsService
	 */
	public ILoggerUtilsService getService() {
		return service;
	}

	/**
	 * setService.
	 * @param service IUtilsService
	 */
	public void setService(final ILoggerUtilsService service) {
		this.service = service;
	}
}
