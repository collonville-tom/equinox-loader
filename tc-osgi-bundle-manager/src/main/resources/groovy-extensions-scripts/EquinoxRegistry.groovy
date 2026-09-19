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

defaultSparkService.get("/help",new Route() {

			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				List<String> cmd=new ArrayList<String>();
				cmd.add("GET:/help -> cette liste");
				cmd.add("GET:/bundle -> liste des bundles");
				cmd.add("GET:/bundle/short -> idem precedent");
				cmd.add("GET:/bundle/:bundleName/:version -> details d'un bundle");
				cmd.add("POST:/bundle/:bundleName/:version/start -> demarrage d'un bundle");
				cmd.add("POST:/bundle/:bundleName/:version/stop -> arret d'un bundle");
				cmd.add("POST:/bundle/:bundleName/:version/uninstall -> desinstallation d'un bundle");
				cmd.add("POST:/bundle/:bundleName/:version/install -> installation d'un bundle");
				cmd.add("GET:/bundle/dependency/:bundleName/:version -> liste des dependances d'un bundle");
				cmd.add("GET:/services -> liste des services");
				return new JsonSerialiser().toJson(cmd);
			}
		});


// Liste des bundles
defaultSparkService.get("/bundle",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		System.out.println(ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList());
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleList();
	}
});
// Liste des bundles en version simple
defaultSparkService.get("/bundle/short",new Route() {

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
defaultSparkService.post("/bundle/:bundleName/:version/start",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleStart(request.params(":bundleName"),request.params(":version"));
	}
});

// arret d'un bundle
defaultSparkService.post("/bundle/:bundleName/:version/stop",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleStop(request.params(":bundleName"),request.params(":version"));
	}
});

// desinstallation d'un bundle
defaultSparkService.post("/bundle/:bundleName/:version/uninstall",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleUninstall(request.params(":bundleName"),request.params(":version"));
	}
});

// installation d'un bundle
defaultSparkService.post("/bundle/:bundleName/:version/install",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleInstall(request.params(":bundleName"),request.params(":version"));
	}
});



// dependances d'un bundle
defaultSparkService.get("/bundle/dependency/:bundleName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleDependencies(request.params(":bundleName"),request.params(":version"));
	}
});

// detail d'un service
defaultSparkService.get("/service/:serviceName/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getEquinoxRegistry().bundleService(request.params(":serviceName"));
	}
});

