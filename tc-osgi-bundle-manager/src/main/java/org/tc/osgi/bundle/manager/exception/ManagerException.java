package org.tc.osgi.bundle.manager.exception;

public class ManagerException extends Exception {

	public ManagerException(final String _message) {
		super(_message);
	}

	public ManagerException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}