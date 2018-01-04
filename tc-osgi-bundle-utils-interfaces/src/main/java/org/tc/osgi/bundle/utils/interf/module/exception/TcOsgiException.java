package org.tc.osgi.bundle.utils.interf.module.exception;

/**
 * UtilsException.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 */
public class TcOsgiException extends Exception {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -2499523546746233719L;

	/**
	 * UtilsException constructor.
	 *
	 * @param _message
	 *            String
	 */
	public TcOsgiException(final String _message) {
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
	public TcOsgiException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}