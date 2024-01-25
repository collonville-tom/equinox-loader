/**
 */
package org.tc.osgi.bundle.utils.interf.pattern.command.exception;

import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

/**
 * CommandExecutionException.java.
 *
 * @author thomas collonvillé
 * @version 0.0.4
 */
public class CommandExecutionException extends TcOsgiException {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -4594974086602271979L;

	/**
	 * CommandExecutionException constructor.
	 *
	 * @param _message
	 *            String
	 */
	public CommandExecutionException(final String _message) {
		super(_message);
	}

	/**
	 * CommandExecutionException constructor.
	 *
	 * @param _message
	 *            String
	 * @param _e
	 *            Throwable
	 */
	public CommandExecutionException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
