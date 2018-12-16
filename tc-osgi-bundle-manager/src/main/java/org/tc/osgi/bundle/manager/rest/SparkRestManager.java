package org.tc.osgi.bundle.manager.rest;



import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.external.RepositoryRegistry;
import org.tc.osgi.bundle.manager.core.internal.EquinoxRegistry;
import org.tc.osgi.bundle.manager.exception.ManagerException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;

import spark.Spark;

public class SparkRestManager{

	//main ici on decrit ce que l'on va vouloir faire avec le manager 
	// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant statique et lancer ses elements
	// on veut pouvoir demander l'ajout d'un bundle et de ses dependances depuis un repo distant dynamique (autre application equinox) et lancer ses elements
	// avoir un referentiel statique et dynamique cnfiguration via un add
	// avoir un repo local
	
	//http://sparkjava.com/documentation#embedded-web-server
	
	private RepositoryRegistry repoRegistry=new RepositoryRegistry();
	private EquinoxRegistry equinoxRegistry;
	
	public SparkRestManager(BundleContext context) throws ManagerException 
	{
		LoggerServiceProxy.getInstance().getLogger(SparkRestManager.class).debug("running spark http server on default port 4567");
		this.equinoxRegistry=new EquinoxRegistry(context);
		initRestCmd();
	}
	
	public SparkRestManager(int port,BundleContext context) throws ManagerException 
	{
		LoggerServiceProxy.getInstance().getLogger(SparkRestManager.class).debug("running spark http server on port "+ port);
		this.equinoxRegistry=new EquinoxRegistry(context);
		Spark.port(port);
		initRestCmd();
	}
	
	
	private void initRestCmd()
	{
		Spark.staticFiles.externalLocation(ManagerPropertyFile.getInstance().getWorkDirectory());
		
		this.equinoxRegistry.buildRegistryCmd();
		this.repoRegistry.buildRegistryCmd();
		
		
	}
	
	
	
	
	
  
}
