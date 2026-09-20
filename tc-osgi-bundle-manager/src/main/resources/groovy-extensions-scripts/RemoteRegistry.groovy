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
		cmd.add("GET:/repository -> initialise l'image du contenu des repos");
		cmd.add("POST:/repository/:name (JSON {\"url\":\"...\"}) -> ajout d'un repository");
		cmd.add("DELETE:/repository/:name -> suppression d'un repository");
		cmd.add("GET:/archive/:tar/:version -> recupere le tar du repository distant");
		cmd.add("POST:/archive/:tar/:version -> pousse un tar dans le repository local");
		cmd.add("POST:/archive/:tar/:version/collect -> demande la recuperation d'un tar depuis un repo distant");
		cmd.add("POST:/archive/:tar/:version/deploy -> lance la procedure de deployement d'une archive tar dans le contexte d'installation de equinox (precede la phase d'installation du bundle contenu dans le tar");
		
		
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

defaultSparkService.post("/repository/:name",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		def json = new groovy.json.JsonSlurper().parseText(request.body());
		return ManagerRmiClient.getInstance().getRemoteRegistry().addRepo(request.params(":name"), json.url);
	}
});

defaultSparkService.delete("/repository/:name",new Route() {

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

defaultSparkService.get("/archive/:tar/:version", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		try {
			byte[] fileData = ManagerRmiClient.getInstance().getRemoteRegistry().serveTar(request.params(TAR_TAG), request.params(VERSION_TAG));
			response.type("application/gzip");
			response.header("Content-Disposition", "attachment; filename=\"" + request.params(TAR_TAG) + "-" + request.params(VERSION_TAG) + ".tar.gz\"");
			javax.servlet.http.HttpServletResponse raw = response.raw();
			raw.getOutputStream().write(fileData);
			raw.getOutputStream().flush();
			raw.getOutputStream().close();
			return raw;
		} catch (java.io.FileNotFoundException e) {
			response.status(404);
			return "File not found";
		}
	}
});

defaultSparkService.delete("/archive/:tar/:version", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		try {
			String processMessage = ManagerRmiClient.getInstance().getRemoteRegistry().removeTar(request.params(TAR_TAG), request.params(VERSION_TAG));
			response.type("application/json");
			return processMessage;
		} catch (java.io.FileNotFoundException e) {
			response.status(404);
			return "File not found";
		}
	}
});

defaultSparkService.post("/archive/:tar/:version", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().receiveTar(request.params(TAR_TAG), request.params(VERSION_TAG), request.bodyAsBytes());
	}
});


defaultSparkService.post("/archive/:tar/:version/collect",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getRemoteRegistry().collectTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});

defaultSparkService.post("/archive/:tar/:version/extract",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getRemoteRegistry().extractTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});









