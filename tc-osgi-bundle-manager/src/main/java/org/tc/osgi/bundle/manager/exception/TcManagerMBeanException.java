package org.tc.osgi.bundle.manager.exception;

public class TcManagerMBeanException extends ManagerException{
	public TcManagerMBeanException(final String _message) {
		super(_message);
	}

	public TcManagerMBeanException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
