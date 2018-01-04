package org.tc.osgi.equinox.loader.cmd;

import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

/**
 * AbstractEquinoxCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public abstract class AbstractEquinoxCmd<T> {

    /**
     * T returnValue.
     */
    protected T returnValue;

    /**
     * execute.
     */
    public abstract void execute() throws EquinoxCmdException;

    /**
     * getReturnValue.
     *
     * @return T
     */
    public T getReturnValue() {
        return this.returnValue;
    }

}
