package org.tc.osgi.bundle.manager.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.core.external.RemoteRepository;
import org.tc.osgi.bundle.manager.core.external.RepositoryRegistry;
import org.tc.osgi.bundle.manager.module.activator.ManagerActivator;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Response;
import spark.Spark;


// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry extends AbstractRegistry {

	
	private BundleContext context;
	
	public EquinoxRegistry(BundleContext context)
	{
		this.context=context;
	}
	
	@Override
	public void buildRegistryCmd() {
		Spark.get("/bundles", (request, response) -> this.bundleList(response));//liste les bundles et leur etat (comme a la console)
		Spark.get("/bundle/:bundleName", (request, response) -> this.bundleInfo(request.params(":bundleName")));//info sur un bundle
		Spark.get("/start/:bundleName", (request, response) -> this.bundleStart(request.params(":bundleName")));//demarrage d'un bundle
		Spark.get("/stop/:bundleName", (request, response) -> this.bundleStop(request.params(":bundleName")));// arret d'un bundle
		Spark.get("/uninstall/:bundleName", (request, response) -> this.bundleUninstall(request.params(":bundleName")));// desinstallation
		Spark.get("/install/:bundleName", (request, response) -> this.bundleInstall(request.params(":bundleName")));// installation
		Spark.get("/services", (request, response) -> this.bundleServices(response));// liste des services
		Spark.get("/service/:serviceName", (request, response) -> this.bundleService(response,request.params(":serviceName")));// details d'un services
		Spark.get("/depends/:bundleName", (request, response) -> this.bundleDependancies(request.params(":bundleName")));// details des dependances d'un bundle
		
	}
	
	private Object bundleDependancies(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Object bundleInfo(String bundleName) {
		// TODO Equivalent commande headers?
		return null;
	}

	private Object bundleService(Response response,String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object bundleServices(Response response) {
		
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive services list");
		List<ServiceWrapper> wrapper=new ArrayList<>();
		
		ServiceReference<?>[] services;
		try {
			services = context.getServiceReferences((String) null, (String) null);
		
		if (services != null) {
			for(ServiceReference<?> service : services) {
				LoggerGestionnary.getInstance(EquinoxRegistry.class).debug("Traitement du service: "+service);
				wrapper.add(new ServiceWrapper(service));
			}
		}
		} catch (InvalidSyntaxException e) {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).error("Erreur dans le recuperation de la liste de services",e);
		}
		return new JsonSerialiser().toJson(wrapper);
	}

	private Object bundleInstall(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleInstaller().processOnBundle(context, bundleName);
			return bundleName +" install";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in stoinstalling bundle "+bundleName,e);
		}
		return "Error in installing bundle "+bundleName;
	}

	private Object bundleStop(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(context, bundleName);
			return bundleName +" stoped";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in stoping bundle "+bundleName,e);
		}
		return "Error in stoping bundle "+bundleName;
	}

	private String bundleUninstall(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleUninstaller().processOnBundle(context, bundleName);
			return bundleName +" uninstall";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in unintalling bundle "+bundleName,e);
		}
		return "Error in unintalling bundle "+bundleName;
	}

	private String bundleStart(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleStarter().processOnBundle(context, bundleName);
			return bundleName +" started";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in starting bundle "+bundleName,e);
		}
		return "Error in starting bundle "+bundleName;
	}



	private String bundleList(Response response) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive bundle list");
		List<BundleWrapper> wrappers=new ArrayList<>();
		for(Bundle b:this.context.getBundles())
		{
			wrappers.add(new BundleWrapper(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}

}
