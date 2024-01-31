package org.tc.osgi.equinox.loader.conf.exception;

import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

/**
 * EquinoxConfigException.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 */
public class EquinoxConfigException extends TcOsgiException {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -8268904103332207680L;

    /**
     * EquinoxConfigException constructor.
     *
     * @param _message
     *            String
     */
    public EquinoxConfigException(final String _message) {
        super(_message);
    }

    /**
     * EquinoxConfigException constructor.
     *
     * @param _message
     *            String
     * @param _e
     *            Throwable
     */
    public EquinoxConfigException(final String _message, final Throwable _e) {
        super(_message, _e);
    }

}
