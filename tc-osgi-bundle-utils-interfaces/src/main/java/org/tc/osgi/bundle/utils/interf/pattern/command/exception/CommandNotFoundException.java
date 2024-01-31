/**
 */
package org.tc.osgi.bundle.utils.interf.pattern.command.exception;

/**
 * CommandNotFoundException.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class CommandNotFoundException extends CommandExecutionException {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 1667350030470229264L;

	/**
	 * CommandNotFoundException constructor.
	 * @param _message String
	 */
	public CommandNotFoundException(final String _message) {
		super(_message);
	}

	/**
	 * CommandNotFoundException constructor.
	 * @param _message String
	 * @param _e Throwable
	 */
	public CommandNotFoundException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
