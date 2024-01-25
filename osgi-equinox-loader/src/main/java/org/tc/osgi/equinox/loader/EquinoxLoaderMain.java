package org.tc.osgi.equinox.loader;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rmi.EquinoxLoaderBundleContextImpl;
import org.tc.osgi.bundle.utils.interf.rmi.IEquinoxLoaderBundleContext;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;
import org.tc.osgi.equinox.loader.cmd.context.FilterValidBundleCmd;
import org.tc.osgi.equinox.loader.cmd.context.LoadDefaultBundleCmd;
import org.tc.osgi.equinox.loader.cmd.context.StartDefaultBundleCmd;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;
import org.tc.osgi.equinox.loader.rmi.EquinoxLoaderRMIServer;
import org.tc.osgi.equinox.loader.starter.EquinoxLoaderException;
import org.tc.osgi.equinox.loader.starter.EquinoxStarter;

/**
 * EquinoxLoaderMain.java.
 *
 * @author Thomas Collonvillé
 * @version 0.0.2
 */
public final class EquinoxLoaderMain {

    /**
     * boolean isAlive.
     */
    public static boolean isAlive = true;
    /**
     * long tempo.
     */
    public static long tempo = 300000;

    /**
     * main.
     *
     * @param args
     *            String[]
     */
    public static void main(final String[] _args) {

        try {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).info("EquinoxStarter-" + EquinoxPropertyFile.getInstance().getVersion() + " start");
            EquinoxStarter.getInstance().compileParameters();
            if (_args.length == 0) {
                LoggerGestionnary.getInstance(EquinoxStarter.class).info("Lancement de l'applicatif sans parametres");
                EquinoxStarter.getInstance().start();
            } else {
                LoggerGestionnary.getInstance(EquinoxStarter.class).info("Lancement de l'applicatif avec " + UtilsServiceImpl.getInstance().tab2String(_args, ","));
                EquinoxStarter.getInstance().start(_args);
            }
            EquinoxLoaderRMIServer.getInstance().addObject(IEquinoxLoaderBundleContext.class.getSimpleName(), new EquinoxLoaderBundleContextImpl());
            if (EquinoxStarter.getInstance().check()) {
            	LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("LoadDefaultBundleCmd");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                new LoadDefaultBundleCmd(EquinoxStarter.getInstance().getContext()).execute();
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("FilterValidBundleCmd");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                new FilterValidBundleCmd(EquinoxStarter.getInstance().getContext()).execute();
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("StartDefaultBundleCmd");
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).debug("####################");
                new StartDefaultBundleCmd(EquinoxStarter.getInstance().getContext()).execute();
            } else {
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("EquinoxStarter not running");
            }
        } catch (final FieldTrackingAssignementException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("Conf error", e);
        } catch (final EquinoxCmdException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("Cmd error", e);
        } catch (final EquinoxLoaderException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("Loader error", e);
        } catch (final EquinoxConfigException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("BundleDir error", e);
        } catch (final RemoteException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("RMI Server error", e);
        } catch (final MalformedURLException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("RMI Server error", e);
        } catch (final UnknownHostException e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("RMI Server error", e);
        } catch (final Throwable e) {
            LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("Error fatal", e);
        }
        while (EquinoxLoaderMain.isAlive) {
            try {
                Thread.sleep(EquinoxLoaderMain.tempo);
            } catch (final InterruptedException e) {
                LoggerGestionnary.getInstance(EquinoxLoaderMain.class).error("Sleep Error", e);
            }
            LoggerGestionnary.getInstance(EquinoxStarter.class).info("Equinox-loader is still alive");
        }

    }

    /**
     * EquinoxLoaderMain constructor.
     */
    private EquinoxLoaderMain() {

    }
}
