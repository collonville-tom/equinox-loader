package org.tc.osgi.bundle.manager.groovy;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.tc.osgi.bundle.manager.mbean.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.rmi.ManagerRmiClient;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;
import org.tc.osgi.bundle.spark.conf.SparkPropertyFile

import spark.Route;
import spark.Response;
import spark.Spark;
import spark.Request;
import spark.Service;

import java.rmi.Naming;
import java.rmi.Remote;

Service defaultSparkService=Service.ignite().port(7654);

defaultSparkService.get("/eqx-cmd",new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				List<String> cmd=new ArrayList<String>();
				cmd.add("/eqx-cmd -> cette liste");
				cmd.add("/bundles -> liste des bundles");
				cmd.add("/bundles/short -> idem precedent");
				cmd.add("/services -> liste des services");
				cmd.add("/bundle/:bundleName/:version -> details d'un bundle");
				cmd.add("/start/:bundleName/:version -> demarrage d'un bundle");
				cmd.add("/stop/:bundleName/:version -> arret d'un bundle");
				cmd.add("/uninstall/:bundleName/:version -> desinstallation d'un bundle");
				cmd.add("/install/:bundleName/:version -> installation d'un bundle");
				cmd.add("/depends/:bundleName/:version -> liste des dependances d'un bundle");
				return new JsonSerialiser().toJson(cmd);
			}
		});


defaultSparkService.get("/debug/:url",new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				try{
					response.type("application/json");
					Remote rem = Naming.lookup("rmi://127.0.0.1:9001/EquinoxRegistryMBean");
					if(rem instanceof EquinoxRegistryMBean) {
						System.out.println("rem est bien un bon groupe, non juste un Eqx Registry");
						return rem.bundleList();
					}
					//	System.out.println(ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList());
					return  ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList();
				}catch (Throwable e) {
						System.out.println(e);
				}
			}
});


// Liste des bundles
defaultSparkService.get("/bundles",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		System.out.println(ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList());
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList();
	}
});
// Liste des bundles en version simple
defaultSparkService.get("/bundles/short",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleShortList();
	}
});

// Liste des services
defaultSparkService.get("/services", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleServices();
	}
});

// Informations sur un bundle
defaultSparkService.get("/bundle/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleInfo(request.params(":bundleName"),request.params(":version"));
	}
});

// demarrage d'un bundle
defaultSparkService.get("/start/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleStart(request.params(":bundleName"),request.params(":version"));
	}
});

// arret d'un bundle
defaultSparkService.get("/stop/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleStop(request.params(":bundleName"),request.params(":version"));
	}
});

// desinstallation d'un bundle
defaultSparkService.get("/uninstall/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleUninstall(request.params(":bundleName"),request.params(":version"));
	}
});

// installation d'un bundle
defaultSparkService.get("/install/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleInstall(request.params(":bundleName"),request.params(":version"));
	}
});

// detail d'un service
defaultSparkService.get("/service/:serviceName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleService(request.params(":serviceName"),request.params(":version"));
	}
});

// dependances d'un bundle
defaultSparkService.get("/depends/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleDependencies(request.params(":bundleName"),request.params(":version"));
	}
});



