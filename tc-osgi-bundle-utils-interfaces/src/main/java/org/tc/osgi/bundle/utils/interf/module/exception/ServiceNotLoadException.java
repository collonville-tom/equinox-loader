/**
 */
package org.tc.osgi.bundle.utils.interf.module.exception;


/**
 * ServiceNotLoadException.java.
 * @author collonville thomas
 * @version 0.1.1
 */
public class ServiceNotLoadException extends TcOsgiException {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -447934465551330931L;

	/**
	 * UtilsException constructor.
	 *
	 * @param _message
	 *            String
	 */
	public ServiceNotLoadException(final String _message) {
		super(_message);
	}

	/**
	 * UtilsException constructor.
	 *
	 * @param _message
	 *            String
	 * @param _e
	 *            Throwable
	 */
	public ServiceNotLoadException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
