package org.tc.osgi.equinox.loader.cmd.context;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.context.BundleStarter;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * StartDefaultBundleCmd.java.
 * @author collonville thomas
 * @version 0.1.4
 */
public class StartDefaultBundleCmd extends AbstractBundleContextCmd {

    private String utilsDependencyBundleName;
    private String utilsDependencyBundleVersion;

    private String managerDependencyBundleName;
    private String managerDependencyBundleVersion;
    
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
            new BundleStarter().processOnBundle(this.context, this.getUtilsDependencyBundleName(),this.getUtilsDependencyBundleVersion());

        } catch (TcOsgiException e) {
            throw new EquinoxCmdException(e.getMessage(), e);
        }
      
        try {
            new BundleStarter().processOnBundle(this.context, this.getManagerDependencyBundleName(),this.getManagerDependencyBundleVersion());

        } catch (TcOsgiException e) {
            LoggerGestionnary.getInstance(StartDefaultBundleCmd.class).error(
                "Lancement auto du bundle echoué :" + this.managerDependencyBundleName + " ce dernier est peut etre simplement absent", e);
        }

    }
    public String getUtilsDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.utilsDependencyBundleName == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "utilsDependencyBundleName");
        }
        return this.utilsDependencyBundleName;
    }
    
    public String getManagerDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.managerDependencyBundleName == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "managerDependencyBundleName");
        }
        return this.managerDependencyBundleName;
    }
    
    public String getUtilsDependencyBundleVersion() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.utilsDependencyBundleVersion == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "utilsDependencyBundleVersion");
        }
        return this.utilsDependencyBundleVersion;
    }
    
    public String getManagerDependencyBundleVersion() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.managerDependencyBundleVersion == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "managerDependencyBundleVersion");
        }
        return this.managerDependencyBundleVersion;
    }

}
