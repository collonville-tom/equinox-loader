package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd;

/**
 * AbstractBundleContextCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public abstract class AbstractBundleContextCmd<T> extends AbstractEquinoxCmd<T> {

 

    /**
     * BundleContext context.
     */
    protected BundleContext context = null;

    /**
     * AbstractBundleContextCmd constructor.
     *
     * @param context
     *            BundleContext
     */
    public AbstractBundleContextCmd(final BundleContext context) {
        this.context = context;
    }

    
}
