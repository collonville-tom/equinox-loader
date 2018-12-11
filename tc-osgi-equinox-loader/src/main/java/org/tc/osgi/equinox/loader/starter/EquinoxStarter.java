package org.tc.osgi.equinox.loader.starter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
// import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * EquinoxStarter.java.
 *
 * @author Thomas Collonvilé
 * @version 0.1.4
 * @track SRS_EQUINOX_LOADER_130
 * @track SRS_EQUINOX_LOADER_140
 */
public final class EquinoxStarter {

    /**
     * EquinoxStarter equinoxInstance.
     */
    private static EquinoxStarter equinoxInstance = null;

    /**
     * getInstance. A utiliser pour initialiser Equinox sans si deja initialiser
     * alors renvoie l'instance courante deja initialisé
     *
     * @return EquinoxStarter
     * @throws FieldTrackingAssignementException
     */
    public static EquinoxStarter getInstance() throws FieldTrackingAssignementException {
        if (EquinoxStarter.equinoxInstance == null) {
            EquinoxStarter.equinoxInstance = new EquinoxStarter();
        }
        return EquinoxStarter.equinoxInstance;
    }

    /**
     * String[] args.
     */
    private final List<String> args = new ArrayList<String>();

    /**
     * String configuration.
     */
    private String configuration = null;

    /**
     * BundleContext context.
     */
    private BundleContext context = null;

    /**
     * String debughLevel.
     */
    private final String debugLevel = null;

    /**
     * String mode.
     */
    private final String mode = null;

    /**
     * EquinoxStarter constructor.
     *
     * @throws FieldTrackingAssignementException
     */
    private EquinoxStarter() throws FieldTrackingAssignementException {

    }

    /**
     * ckeck.
     *
     * @return boolean
     */
    public boolean check() {
        return EclipseStarter.isRunning();

    }

    public void compileParameters() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        this.args.add(this.getMode());
        if (!this.getConfiguration().equals("")) {
            this.args.add("-configuration");
            this.args.add(this.getConfiguration());
        }
        this.args.add(this.getDebugLevel());
    }

    /**
     * getMode.
     *
     * @return String
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     * @throws EquinoxConfigException
     */
    public String getConfiguration() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.configuration == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "configuration");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Configuration :" + this.configuration);
        return this.configuration;
    }

    /**
     * getContext.
     *
     * @return BundleContext
     */
    public BundleContext getContext() {
    	if(this.context==null)
    		LoggerGestionnary.getInstance(EquinoxStarter.class).warn("BundleContext is null maybe provoque major error");
        return this.context;
    }

    /**
     * getMode.
     *
     * @return String
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     * @throws EquinoxConfigException
     */
    public String getDebugLevel() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.debugLevel == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "debugLevel");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("DebugLevel :" + this.debugLevel);
        return this.debugLevel;
    }

    /**
     * getMode.
     *
     * @return String
     * @throws FieldTrackingAssignementException
     * @throws EquinoxCmdException
     * @throws EquinoxConfigException
     */
    public String getMode() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
        if (this.mode == null) {
            XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "mode");
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Mode :" + this.mode);
        return this.mode;
    }

    /**
     * setConfiguration. FOR TEST ONLY
     * @param value String
     */
    public void setConfiguration(final String value) {
        this.configuration = value;
    }

    /**
     * start.
     * Cette methode doit etre employer pour injecter des valeur par defauts de config associé au fichier xml
     *
     * @throws EquinoxLoaderException
     */
    public void start() throws EquinoxLoaderException {
        final String[] argType = {};
        LoggerGestionnary.getInstance(EquinoxStarter.class).info("Lancement de l'applicatif avec les arguments xml");
        this.start(this.args.toArray(argType));

    }

    /**
     * start.
     * Cette methode doit etre employer pour injecter des valeurs specifique a la place du fichier de conf xml (attention a reprendre les parametres tel que -console, etc...)
     * http://www.javased.com/index.php?api=org.eclipse.core.runtime.adaptor.EclipseStarter
     * http://alvinalexander.com/java/jwarehouse/eclipse/org.eclipse.osgi/eclipseAdaptor/src/org/eclipse/core/runtime/adaptor/EclipseStarter.java.shtml
     * @throws Exception
     */
    public void start(final String[] _args) throws EquinoxLoaderException {
        try {
            if (!EclipseStarter.isRunning()) {
                EclipseStarter.startup(_args, new Thread("EQUINOX-THREAD"));
                if (this.context == null) {
                    LoggerGestionnary.getInstance(EquinoxStarter.class).warn("Le context bundle n'a pas ete correctement construit");
                }
                LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Construction du context bundle a partir du system bundle context");
                this.context = EclipseStarter.getSystemBundleContext();
            }
        } catch (final Exception e) {
            throw new EquinoxLoaderException("Une erreur inconnue est survenue", e);
        }

        // Si c'est felix qui est utilisé TODO car en l'etat ca marche po
        // final FrameworkFactory factory = new FrameworkFactory();
        // final Framework framework = factory.newFramework(null);
        // this.context = framework.getBundleContext();
    }
}
