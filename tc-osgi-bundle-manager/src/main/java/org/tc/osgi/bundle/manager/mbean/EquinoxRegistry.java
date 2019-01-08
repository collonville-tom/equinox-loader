package org.tc.osgi.bundle.manager.mbean;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.wrapper.BundleControlWrapper;
import org.tc.osgi.bundle.manager.core.wrapper.BundleHeaderWrapper;
import org.tc.osgi.bundle.manager.core.wrapper.BundleWrapper;
import org.tc.osgi.bundle.manager.core.wrapper.BundleWrapperShortDescription;
import org.tc.osgi.bundle.manager.core.wrapper.ServiceWrapper;
import org.tc.osgi.bundle.manager.exception.TcEquinoxRegistryException;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;



// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry implements EquinoxRegistryMBean {

	private BundleContext context;

	private static final String BUNDLE_CLASSIFIER = "-assembly.jar";
	
	public EquinoxRegistry(BundleContext context) {
		this.context = context;
	}

	
	
	public Bundle retrieveBundle(String bundleName) throws TcEquinoxRegistryException {
		for (Bundle b : this.context.getBundles()) {
			if (b.getSymbolicName().equals(bundleName)) {
				return b;
			}
		}
		throw new TcEquinoxRegistryException("Bundle " + bundleName + " not found");
	}
	
	public String buildPath(String bundleName, String version)	throws FieldTrackingAssignementException{
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
	
	
	public String bundleDependencies(String bundleName, String version) {
		String bundleControlFile=ManagerPropertyFile.getInstance().getBundlesDirectory()+"/"+bundleName+"-"+version+"/control";
		BundleControlWrapper wrapper;
		try {
			wrapper = new BundleControlWrapper(bundleControlFile);
			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistryException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la determination des dependannces du bundle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la determination des dependannces du bundle ";
	}
	
	
	

	public String bundleInfo(String bundleName, String version) {
		try {
			Bundle b = this.retrieveBundle(bundleName);
			BundleHeaderWrapper wrapper=new BundleHeaderWrapper(b.getHeaders());
			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistryException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la recuperation des services du bunddle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la recuperation des info du bunddle ";
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
					LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).debug("Traitement du service: " + service);
					wrapper.add(new ServiceWrapper(service));
				}
			}

			return new JsonSerialiser().toJson(wrapper);
		} catch (TcEquinoxRegistryException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error(
					"Une erreur s'est produite lors de la recuperation des services du bunddle " + bundleName, e);
		}
		return "Une erreur s'est produite lors de la recuperation des services du bunddle ";
	}

	
	public String bundleServices() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive services list");
		List<ServiceWrapper> wrapper = new ArrayList<>();

		ServiceReference<?>[] services;
		try {
			services = context.getServiceReferences((String) null, (String) null);
			if (services != null) {
				for (ServiceReference<?> service : services) {
					LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).debug("Traitement du service: " + service);
					wrapper.add(new ServiceWrapper(service));
				}
			}
		} catch (InvalidSyntaxException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Erreur dans le recuperation de la liste de services", e);
		}
		return new JsonSerialiser().toJson(wrapper);
	}

	
	public String bundleInstall(String bundleName, String version) {
		try {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			String bundlePath = this.buildPath(bundleName, version);
			BundleUtilsServiceProxy.getInstance().getBundleInstaller().processOnBundle(context, bundlePath,version);
			return bundleName + " install";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoinstalling bundle " + bundleName, e);
		}
		return "Error in installing bundle " + bundleName;
	}
	


	public String bundleStop(String bundleName, String version) {
		try {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(context, bundleName,version);
			return bundleName + " stoped";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in stoping bundle " + bundleName, e);
		}
		return "Error in stoping bundle " + bundleName;
	}
	


	public String bundleUninstall(String bundleName, String version) {
		try {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleUninstaller().processOnBundle(context, bundleName,version);
			return bundleName + " uninstall";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in unintalling bundle " + bundleName, e);
		}
		return "Error in unintalling bundle " + bundleName;
	}
	

	public String bundleStart(String bundleName, String version) {
		try {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).warn("Parameter version is not used yet "+version);
			BundleUtilsServiceProxy.getInstance().getBundleStarter().processOnBundle(context, bundleName,version);
			return bundleName + " started";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class)
					.error("Error in starting bundle " + bundleName, e);
		}
		return "Error in starting bundle " + bundleName;
	}
	

	public String bundleList() {
		LoggerServiceProxy.getInstance().getLogger(RemoteRegistry.class).debug("Retreive bundle list");
		List<BundleWrapper> wrappers = new ArrayList<>();
		for (Bundle b : this.context.getBundles()) {
			wrappers.add(new BundleWrapper(b));
		}
		return new JsonSerialiser().toJson(wrappers);
	}
	

}
