package org.tc.osgi.equinox.loader.cmd.exception;

/**
 * EquinoxCmdException.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class EquinoxCmdException extends Exception {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -7899645183463727510L;

    /**
     * EquinoxCmdException constructor.
     *
     * @param _message
     *            String
     */
    public EquinoxCmdException(final String _message) {
        super(_message);
    }

    /**
     * EquinoxCmdException constructor.
     *
     * @param _message
     *            String
     * @param _e
     *            Throwable
     */
    public EquinoxCmdException(final String _message, final Throwable _e) {
        super(_message, _e);
    }

}
