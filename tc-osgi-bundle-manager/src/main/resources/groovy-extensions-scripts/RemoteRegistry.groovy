package org.tc.osgi.bundle.manager.groovy;

import java.lang.Exception;
import java.nio.file.Files
import java.util.ArrayList;
import java.util.List;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile
import org.tc.osgi.bundle.manager.mbean.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.rmi.ManagerRmiClient;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;

import spark.Route;
import spark.Response;
import spark.Spark;
import spark.Request;
import spark.Service;

Service defaultSparkService=Service.ignite().port(4567);

defaultSparkService.staticFiles.externalLocation("/var/equinox-loader-manager/");

String TAR_TAG = ":tar";
String VERSION_TAG = ":version";



defaultSparkService.get("/help",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		List<String> cmd=new ArrayList<String>();
		cmd.add("/help -> cette liste");
		cmd.add("/repository -> initialise l'image du contenu des repos");
		cmd.add("/repository/:name/add (POST JSON {\"url\":\"...\"}) -> ajout d'un repository");
		cmd.add("/repository/:name/delete -> suppression d'un repository");
		cmd.add("/archive/:tar/:version/pull -> importe un tar depuis un repository distant et le depose dans le repository local");
		cmd.add("/archive/:tar/:version/extract -> permet pour un client de l'interface REST de realiser l'extraction d'un TAR contenu dans le repo local");
		cmd.add("/archive/:tar/:version/deploy -> lance la procedure de deployement d'une archive tar dans le contexte d'installation de equinox (precede la phase d'installation du bundle contenu dans le tar");
		
		
		return new JsonSerialiser().toJson(cmd);
	}
});


defaultSparkService.get("/repository",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		try {
			return ManagerRmiClient.getInstance().getRemoteRegistry().fetchRepo();
		}
		catch (Throwable e) {
			System.out.println(e);
		}
		return null;
	}
});

defaultSparkService.post("/repository/:name/add",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		def json = new groovy.json.JsonSlurper().parseText(request.body());
		return ManagerRmiClient.getInstance().getRemoteRegistry().addRepo(request.params(":name"), json.url);
	}
});

defaultSparkService.post("/repository/:name/delete",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().delRepo(request.params(":name"));
	}
});


defaultSparkService.get("/repository/:name/fetch",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().fetchRemoteRepo();
	}
});

defaultSparkService.get("/archive/:tar/:version/pull", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().pullTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});

defaultSparkService.get("/archive/:tar/:version/extract",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String b=ManagerRmiClient.getInstance().getRemoteRegistry().pushTar(request.params(TAR_TAG), request.params(VERSION_TAG));
		response.redirect(b.toString());
		return "Redirection to " + b;
	}
});

defaultSparkService.get("/archive/:tar/:version/deploy",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getRemoteRegistry().deployTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});









