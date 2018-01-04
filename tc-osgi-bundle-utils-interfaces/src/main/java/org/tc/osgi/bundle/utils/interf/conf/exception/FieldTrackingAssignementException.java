package org.tc.osgi.bundle.utils.interf.conf.exception;

import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;

/**
 * FieldTrackingAssignementException.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 */
public class FieldTrackingAssignementException extends TcOsgiException {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 6825556014288915007L;

	/**
	 * FieldTrackingAssignementException constructor.
	 *
	 * @param _message
	 *            String
	 */
	public FieldTrackingAssignementException(final String _message) {
		super(_message);
	}

	/**
	 * FieldTrackingAssignementException constructor.
	 *
	 * @param _message
	 *            String
	 * @param _e
	 *            Throwable
	 */
	public FieldTrackingAssignementException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
