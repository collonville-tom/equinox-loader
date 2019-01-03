package org.tc.osgi.bundle.manager.core.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.core.external.RemoteRegistry;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleControlWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleHeaderWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleWrapper;
import org.tc.osgi.bundle.manager.core.internal.wrapper.BundleWrapperShortDescription;
import org.tc.osgi.bundle.manager.core.internal.wrapper.ServiceWrapper;
import org.tc.osgi.bundle.manager.exception.TcEquinoxRegistry;
import org.tc.osgi.bundle.manager.jmx.interf.registry.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

import spark.Response;
import spark.Spark;

// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry extends AbstractRegistry implements EquinoxRegistryMBean {

	private BundleContext context;

	public static final String BUNDLE_CLASSIFIER = "-assembly.jar";
	
	public EquinoxRegistry(BundleContext context) {
		this.context = context;
	}

	@Override
	public void buildRegistryCmd() {
		Spark.get("/bundles", (request, response) -> this.bundleListResponse(response));// liste les bundles et leur etat (comme
																				// a la console
		Spark.get("/bundles/short", (request, response) -> this.bundleShortListResponse(response));// liste les bundles et leur etat en simplifié
		Spark.get("/services", (request, response) -> this.bundleServicesResponse(response));// liste des services
		
		Spark.get("/bundle/:bundleName/:version", (request, response) -> this.bundleInfoResponse(response,request.params(":bundleName"),request.params(":version")));// info
																												// sur
																												// un
																												// bundle
		Spark.get("/start/:bundleName/:version", (request, response) -> this.bundleStartResponse(request.params(":bundleName"),request.params(":version")));// demarrage
																												// d'un
																												// bundle
		Spark.get("/stop/:bundleName/:version", (request, response) -> this.bundleStopResponse(request.params(":bundleName"),request.params(":version")));// arret
																												// d'un
																												// bundle
		Spark.get("/uninstall/:bundleName/:version", (request, response) -> this.bundleUninstallResponse(request.params(":bundleName"),request.params(":version")));// desinstallation
		Spark.get("/install/:bundleName/:version", (request, response) -> this.bundleInstallResponse(request.params(":bundleName"),request.params(":version")));// installation
		Spark.get("/service/:serviceName/:version",
				(request, response) -> this.bundleServiceResponse(response, request.params(":serviceName")));// details d'un
		Spark.get("/depends/:bundleName/:version",
				(request, response) -> this.bundleDependenciesResponse(response,request.params(":bundleName"),request.params(":version")));// details des
																								// dependances d'un
																								// bundle
	}
	
	private Bundle retrieveBundle(String bundleName) throws TcEquinoxRegistry {
		for (Bundle b : this.context.getBundles()) {
			if (b.getSymbolicName().equals(bundleName)) {
				return b;
			}
		}
		throw new TcEquinoxRegistry("Bundle " + bundleName + " not found");
	}
	
	private String buildPath(String bundleName, String version)	throws FieldTrackingAssignementException{
		StringBuilder builder = new StringBuilder(ManagerPropertyFile.getInstance().getBundleLocalBase());
		builder.append(ManagerPropertyFile.getInstance().getBundleDirectory()).append("/");
		builder.append(bundleName).append("-").append(version);
		builder.append(BUNDLE_CLASSIFIER);
		return builder.toString();
	}

	public String bundleShortList() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive bundle list");
		List<BundleWrapperShortDescription> wrappers = new ArrayList<>();
		for (Bundle b : this.context.getBundles()) {
			wrappers.add(new BundleWrapperShortDescription(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}	
	
	private String bundleShortListResponse(Response response) {
		response.type("application/json");
		return this.bundleShortList();
	}

	public String bundleDependencies(String bundleName, String version) {
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
	
	private String bundleDependenciesResponse(Response response, String bundleName, String version) {
		response.type("application/json");
		return this.bundleDependencies(bundleName,version);
	}
	
	

	public String bundleInfo(String bundleName, String version) {
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

	private String bundleInfoResponse(Response response, String bundleName, String version) {
		response.type("application/json");
		return this.bundleInfo(bundleName,version);
	}


	public String bundleService(String bundleName) {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive service list");
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

	private String bundleServiceResponse(Response response, String bundleName) {
		response.type("application/json");
		return this.bundleService(bundleName);
	}
	
	public String bundleServices() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive services list");
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

	private String bundleServicesResponse(Response response) {
		response.type("application/json");
		return this.bundleServices();
	}
	
	public String bundleInstall(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			String bundlePath = this.buildPath(bundleName, version);
			BundleUtilsServiceProxy.getInstance().getBundleInstaller().processOnBundle(context, bundlePath,version);
			return bundleName + " install";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoinstalling bundle " + bundleName, e);
		}
		return "Error in installing bundle " + bundleName;
	}
	
	private String bundleInstallResponse(String bundleName, String version) {
		return this.bundleInstall(bundleName, version);
	}

	public String bundleStop(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(context, bundleName,version);
			return bundleName + " stoped";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoping bundle " + bundleName, e);
		}
		return "Error in stoping bundle " + bundleName;
	}
	
	private String bundleStopResponse(String bundleName, String version) {
		return this.bundleStop(bundleName, version);
	}

	public String bundleUninstall(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleUninstaller().processOnBundle(context, bundleName,version);
			return bundleName + " uninstall";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in unintalling bundle " + bundleName, e);
		}
		return "Error in unintalling bundle " + bundleName;
	}
	
	private String bundleUninstallResponse(String bundleName, String version) {
		return this.bundleUninstall(bundleName, version);
	}

	public String bundleStart(String bundleName, String version) {
		try {
			LoggerGestionnary.getInstance(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleStarter().processOnBundle(context, bundleName,version);
			return bundleName + " started";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in starting bundle " + bundleName, e);
		}
		return "Error in starting bundle " + bundleName;
	}
	
	private String bundleStartResponse(String bundleName, String version)
	{
		return this.bundleStart(bundleName, version);
	}

	public String bundleList() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive bundle list");
		List<BundleWrapper> wrappers = new ArrayList<>();
		for (Bundle b : this.context.getBundles()) {
			wrappers.add(new BundleWrapper(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}
	
	private String bundleListResponse(Response response) {
		response.type("application/json");
		return this.bundleList();
	}

}
