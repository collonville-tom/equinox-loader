package org.tc.osgi.equinox.loader.cmd.context;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
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
