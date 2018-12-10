package org.tc.osgi.bundle.manager.core.internal;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

import spark.Spark;


// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry extends AbstractRegistry {

	
	private BundleContext context;
	
	public EquinoxRegistry()
	{
		try {
			this.context=BundleUtilsServiceProxy.getInstance().getBundleContext();
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in initilizing EquinoxRegistry",e);
		}
	}
	
	@Override
	public void buildRegistryCmd() {
		Spark.get("/bundles", (request, response) -> this.bundleList());//liste les bundles et leur etat (comme a la console)
		Spark.get("/bundle/:bundleName", (request, response) -> this.bundleInfo(request.params(":bundleName")));//info sur un bundle
		Spark.get("/start/:bundleName", (request, response) -> this.bundleStart(request.params(":bundleName")));//demarrage d'un bundle
		Spark.get("/stop/:bundleName", (request, response) -> this.bundleStop(request.params(":bundleName")));// arret d'un bundle
		Spark.get("/uninstall/:bundleName", (request, response) -> this.bundleUninstall(request.params(":bundleName")));// desinstallation
		Spark.get("/install/:bundleName", (request, response) -> this.bundleInstall(request.params(":bundleName")));// installation
		Spark.get("/services", (request, response) -> this.bundleServices());// liste des services
		Spark.get("/service/:serviceName", (request, response) -> this.bundleService(request.params(":serviceName")));// details d'un services
		
		
	}

	private Object bundleService(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object bundleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object bundleInstall(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object bundleStop(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(context, bundleName);
			return bundleName +"stoped";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in stoping bundle "+bundleName,e);
		}
		return "Error in stoping bundle "+bundleName;
	}

	private Object bundleUninstall(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	private String bundleStart(String bundleName) {
		try {
			BundleUtilsServiceProxy.getInstance().getBundleStarter().processOnBundle(context, bundleName);
			return bundleName +"started";
		} catch (TcOsgiException e) {
			LoggerServiceProxy.getInstance().getLogger(EquinoxRegistry.class).error("Error in starting bundle "+bundleName,e);
		}
		return "Error in starting bundle "+bundleName;
	}

	private Object bundleInfo(String bundleName) {
		// TODO Auto-generated method stub
		return null;
	}

	private String bundleList() {
		// TODO Auto-generated method stub
		return null;
	}

}
