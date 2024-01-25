package org.tc.osgi.equinox.loader.rmi;

import java.rmi.RemoteException;

import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.rmi.server.AbstractRMIServer;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;
import org.tc.osgi.equinox.loader.starter.EquinoxStarter;

/**
 * EquinoxLoaderRMIServer.java.
 * @author collonville thomas
 * @version 0.1.4
 */
public class EquinoxLoaderRMIServer extends AbstractRMIServer {

    /**
     * EquinoxLoaderRMIServer instance.
     */
    private static EquinoxLoaderRMIServer instance = null;

    /**
     * getInstance.
     * @return EquinoxLoaderRMIServer
     * @throws RemoteException
     * @throws FieldTrackingAssignementException
     */
    public synchronized static EquinoxLoaderRMIServer getInstance() throws RemoteException, FieldTrackingAssignementException {
        if (EquinoxLoaderRMIServer.instance == null) {
            EquinoxLoaderRMIServer.instance = new EquinoxLoaderRMIServer();
        }
        return EquinoxLoaderRMIServer.instance;
    }

    /**
     * String rmiAddr.
     */
    private String rmiAddr;
    /**
     * String rmiPort.
     */
    private String rmiPort;

    /**
     * EquinoxLoaderRMIServer constructor.
     * @throws RemoteException
     * @throws FieldTrackingAssignementException
     */
    private EquinoxLoaderRMIServer() throws RemoteException, FieldTrackingAssignementException {
        super();
        createRegistry(getPort());
    }

    /**
     * @return String
     * @throws FieldTrackingAssignementException
     * @see org.tc.osgi.bundle.utils.rpc.IRPCServer#getAddr()
     */
    @Override
    public String getAddr() throws FieldTrackingAssignementException {
        if (rmiAddr == null) {
            try {
                XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiAddr");
            } catch (final EquinoxConfigException e) {
                throw new FieldTrackingAssignementException("EquinoxConfigException", e);
            } catch (final EquinoxCmdException e) {
                throw new FieldTrackingAssignementException("EquinoxCmdException", e);
            }
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Recuperation adresse ecoute RMI :" + rmiAddr);
        return rmiAddr;
    }

    /**
     * @return String
     * @throws FieldTrackingAssignementException
     * @see org.tc.osgi.bundle.utils.rpc.IRPCServer#getPort()
     */
    @Override
    public String getPort() throws FieldTrackingAssignementException {
        if (rmiPort == null) {
            try {
                XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "rmiPort");
            } catch (final EquinoxConfigException e) {
                throw new FieldTrackingAssignementException("EquinoxConfigException", e);
            } catch (final EquinoxCmdException e) {
                throw new FieldTrackingAssignementException("EquinoxCmdException", e);
            }
        }
        LoggerGestionnary.getInstance(EquinoxStarter.class).debug("Recuperation port RMI :" + rmiPort);
        return rmiPort;
    }

}
