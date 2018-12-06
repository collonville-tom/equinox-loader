package org.tc.osgi.bundle.manager.rest;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.tc.osgi.bundle.manager.core.ManagerRegistry;
import org.tc.osgi.bundle.manager.exception.ManagerException;
import org.tc.osgi.bundle.manager.module.activator.ManagerActivator;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public class IManagerCmdImpl implements IManagerCmd{

	//main ici on decrit ce que l'on va vouloir faire avec le manager 
	// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant statique et lancer ses elements
	// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant dynamique (autre application equinox) et lancer ses elements
	// avoir un referentiel statique et dynamique cnfiguration via un add
	// avoir un repo local
	private ManagerRegistry registry=new ManagerRegistry();
	private RestManager server;
	
	public IManagerCmdImpl() throws ManagerException 
	{
		
		try {
			this.initServer();
		} catch (RemoteException | MalformedURLException | UnknownHostException | FieldTrackingAssignementException e) {
			throw new ManagerException("Demarrage du service rest manager en echec",e);
		}
		
	}
	
	
	
	public void fetchDefaultRepository()
	{
		registry.initStaticRepository();
		LoggerServiceProxy.getInstance().getLogger(ManagerActivator.class).debug(registry.toString());
	}
	
    protected void initServer() throws RemoteException, MalformedURLException, UnknownHostException, FieldTrackingAssignementException {
    	server = new RestManager();
    	server.addObject(IManagerCmd.class.getSimpleName(), this);
    }
}
