package org.tc.osgi.bundle.manager.exception;

import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;

public class ManagerException extends TcOsgiException {

	public ManagerException(final String _message) {
		super(_message);
	}

	public ManagerException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}