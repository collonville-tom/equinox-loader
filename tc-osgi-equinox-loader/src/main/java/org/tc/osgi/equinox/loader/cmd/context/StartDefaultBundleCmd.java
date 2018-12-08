package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.context.BundleStarter;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;
import org.tc.osgi.equinox.loader.starter.EquinoxStarter;

/**
 * StartDefaultBundleCmd.java.
 * @author collonville thomas
 * @version 0.1.4
 */
public class StartDefaultBundleCmd extends AbstractBundleContextCmd {

	private String consoleDependencyBundleName;
    /**
     * String utilsDependencyBundleName.
     */
    private String utilsDependencyBundleName;

    private String managerDependencyBundleName;
    
    /**
     * StartDefaultBundleCmd constructor.
     * @param context BundleContext
     */
    public StartDefaultBundleCmd(final BundleContext context) {
        super(context);
    }

    /**
     * @throws EquinoxCmdException
     * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
     */
    @Override
    public void execute() throws EquinoxCmdException {
        try {
            new BundleStarter().processOnBundle(this.context, this.getUtilsDependencyBundleName());

        } catch (TcOsgiException | EquinoxConfigException e) {
            throw new EquinoxCmdException(e.getMessage(), e);
        }
        try {
            new BundleStarter().processOnBundle(this.context, this.getConsoleDependencyBundleName());

        } catch (TcOsgiException | EquinoxConfigException e) {
            LoggerGestionnary.getInstance(EquinoxStarter.class).error(
                "Lancement auto du bundle echoué :" + this.consoleDependencyBundleName + " ce dernier est peut etre simplement absent, equinox ne fournira pas de mode console", e);
        }
        try {
            new BundleStarter().processOnBundle(this.context, this.getManagerDependencyBundleName());

        } catch (TcOsgiException | EquinoxConfigException e) {
            LoggerGestionnary.getInstance(EquinoxStarter.class).error(
                "Lancement auto du bundle echoué :" + this.managerDependencyBundleName + " ce dernier est peut etre simplement absent", e);
        }

    }

    public String getConsoleDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.consoleDependencyBundleName == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "consoleDependencyBundleName");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Lancement auto du bundle :" + this.consoleDependencyBundleName);
        return this.consoleDependencyBundleName;
    }
    /**
     * getUtilsDependencyBundleName.
     * @return String
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     * @throws EquinoxConfigException
     */
    public String getUtilsDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.utilsDependencyBundleName == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "utilsDependencyBundleName");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Lancement auto du bundle :" + this.utilsDependencyBundleName);
        return this.utilsDependencyBundleName;
    }
    
    public String getManagerDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.managerDependencyBundleName == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "managerDependencyBundleName");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Lancement auto du bundle :" + this.managerDependencyBundleName);
        return this.managerDependencyBundleName;
    }

}
