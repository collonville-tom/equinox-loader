package org.tc.osgi.bundle.manager.core.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.core.external.RemoteRepository;
import org.tc.osgi.bundle.manager.core.external.RepositoryRegistry;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleControlWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleHeaderWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleWrapperShortDescription;
import org.tc.osgi.bundle.manager.core.internal.wrapper.ServiceWrapper;
import org.tc.osgi.bundle.manager.exception.TcEquinoxRegistry;
import org.tc.osgi.bundle.manager.module.activator.ManagerActivator;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;


import spark.Response;
import spark.Spark;

// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry extends AbstractRegistry {

	private BundleContext context;

	public EquinoxRegistry(BundleContext context) {
		this.context = context;
	}

	@Override
	public void buildRegistryCmd() {
		Spark.get("/bundles", (request, response) -> this.bundleList(response));// liste les bundles et leur etat (comme
																				// a la console
		Spark.get("/bundles/short", (request, response) -> this.bundleShortList(response));// liste les bundles et leur etat en simplifié
		Spark.get("/services", (request, response) -> this.bundleServices(response));// liste des services
		
		Spark.get("/bundle/:bundleName/:version", (request, response) -> this.bundleInfo(response,request.params(":bundleName"),request.params(":version")));// info
																												// sur
																												// un
																												// bundle
		Spark.get("/start/:bundleName/:version", (request, response) -> this.bundleStart(request.params(":bundleName"),request.params(":version")));// demarrage
																												// d'un
																												// bundle
		Spark.get("/stop/:bundleName/:version", (request, response) -> this.bundleStop(request.params(":bundleName"),request.params(":version")));// arret
																												// d'un
																												// bundle
		Spark.get("/uninstall/:bundleName/:version", (request, response) -> this.bundleUninstall(request.params(":bundleName"),request.params(":version")));// desinstallation
		Spark.get("/install/:bundleName/:version", (request, response) -> this.bundleInstall(request.params(":bundleName"),request.params(":version")));// installation
		Spark.get("/service/:serviceName/:version",
				(request, response) -> this.bundleService(response, request.params(":serviceName")));// details d'un
		Spark.get("/depends/:bundleName/:version",
				(request, response) -> this.bundleDependencies(response,request.params(":bundleName"),request.params(":version")));// details des
																								// dependances d'un
																								// bundle
	}

	
	
	private Object bundleShortList(Response response) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive bundle list");
		List<BundleWrapperShortDescription> wrappers = new ArrayList<>();
		for (Bundle b : this.context.getBundles()) {
			wrappers.add(new BundleWrapperShortDescription(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}

	private Object bundleDependencies(Response response, String bundleName, String version) {
		response.type("application/json");
		String bundleControlFile=ManagerPropertyFile.getInstance().getBundlesDirectory()+"/"+bundleName+"-"+version+"/control";
		BundleControlWrapper wrapper;
		try {
			wrapper = new BundleControlWrapper(bundleControlFile);
			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistry e) {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la determination des dependannces du bundle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la determination des dependannces du bundle ";
	}

	private Object bundleInfo(Response response, String bundleName, String version) {
		response.type("application/json");
		try {
			Bundle b = this.retrieveBundle(bundleName);
			BundleHeaderWrapper wrapper=new BundleHeaderWrapper(b.getHeaders());
			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistry e) {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la recuperation des services du bunddle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la recuperation des info du bunddle ";
	}

	private Bundle retrieveBundle(String bundleName) throws TcEquinoxRegistry {
		for (Bundle b : this.context.getBundles()) {
			if (b.getSymbolicName().equals(bundleName)) {
				return b;
			}
		}
		throw new TcEquinoxRegistry("Bundle " + bundleName + " not found");
	}

	private Object bundleService(Response response, String bundleName) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive service list");
		List<ServiceWrapper> wrapper = new ArrayList<>();
		try {
			Bundle bundle = this.retrieveBundle(bundleName);

			ServiceReference<?>[] services;

			services = bundle.getRegisteredServices();

			if (services != null) {
				for (ServiceReference<?> service : services) {
					LoggerGestionnary.getInstance(EquinoxRegistry.class).debug("Traitement du service: " + service);
					wrapper.add(new ServiceWrapper(service));
				}
			}

			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistry e) {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la recuperation des services du bunddle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la recuperation des services du bunddle ";
	}

	private Object bundleServices(Response response) {

		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive services list");
		List<ServiceWrapper> wrapper = new ArrayList<>();

		ServiceReference<?>[] services;
		try {
			services = context.getServiceReferences((String) null, (String) null);

			if (services != null) {
				for (ServiceReference<?> service : services) {
					LoggerGestionnary.getInstance(EquinoxRegistry.class).debug("Traitement du service: " + service);
					wrapper.add(new ServiceWrapper(service));
				}
			}
		} catch (InvalidSyntaxException e) {
			LoggerGestionnary.getInstance(EquinoxRegistry.class)
					.error("Erreur dans le recuperation de la liste de services", e);
		}
		return new JsonSerialiser().toJson(wrapper);
	}

	private Object bundleInstall(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleInstaller().processOnBundle(context, bundleName);
			return bundleName + " install";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoinstalling bundle " + bundleName, e);
		}
		return "Error in installing bundle " + bundleName;
	}

	private Object bundleStop(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(context, bundleName);
			return bundleName + " stoped";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoping bundle " + bundleName, e);
		}
		return "Error in stoping bundle " + bundleName;
	}

	private String bundleUninstall(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleUninstaller().processOnBundle(context, bundleName);
			return bundleName + " uninstall";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in unintalling bundle " + bundleName, e);
		}
		return "Error in unintalling bundle " + bundleName;
	}

	private String bundleStart(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleStarter().processOnBundle(context, bundleName);
			return bundleName + " started";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in starting bundle " + bundleName, e);
		}
		return "Error in starting bundle " + bundleName;
	}

	private String bundleList(Response response) {
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Retreive bundle list");
		List<BundleWrapper> wrappers = new ArrayList<>();
		for (Bundle b : this.context.getBundles()) {
			wrappers.add(new BundleWrapper(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}

}
