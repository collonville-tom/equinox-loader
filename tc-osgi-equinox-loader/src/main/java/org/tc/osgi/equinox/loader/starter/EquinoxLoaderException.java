/**
 */
package org.tc.osgi.equinox.loader.starter;

import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

/**
 * EquinoxLoaderException.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 */
public class EquinoxLoaderException extends TcOsgiException {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -6667068862949874950L;

    /**
     * EquinoxLoaderException constructor.
     *
     * @param _message
     *            String
     */
    public EquinoxLoaderException(final String _message) {
        super(_message);
    }

    /**
     * EquinoxLoaderException constructor.
     *
     * @param _message
     *            String
     * @param _e
     *            Throwable
     */
    public EquinoxLoaderException(final String _message, final Throwable _e) {
        super(_message, _e);
    }
}
