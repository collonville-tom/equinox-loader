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
import org.tc.osgi.bundle.manager.mbean.ArchiveRegistryMBean;
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



defaultSparkService.get("/repository/help",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		List<String> cmd=new ArrayList<String>();
		cmd.add("/help -> cette liste");
		cmd.add("/repository/local -> initialise l'image local du contenu du repo local");
		cmd.add("/repository/local2 -> initialise l'image local du contenu du repo local");
		cmd.add("/repository/remote/:name/add/:url -> ajout d'un repository, remplacer les / par des %2F par exemple: http:%2F%2Ftoto%2Ftata");
		cmd.add("/repository/remote/:name/delete -> suppression d'un repository, not implemented yet");
		cmd.add("/repository/remote/:name/fetch -> initialise l'image local du contenu du repo distant");
		cmd.add("/repository/remote -> initialise l'image local du contenu des repo distant (fetch all)");
		cmd.add("/archive/:tar/:version/pull -> importe un tar depuis un repository distant et le depose dans le repository local");
		cmd.add("/archive/:tar/:version/deploy -> lance la procedure de deployement d'une archive tar dans le contexte d'installation de equinox (precede la phase d'installation du bundle contenu dans le tar");

		
		return new JsonSerialiser().toJson(cmd);
	}
});

defaultSparkService.get("/repository/local",new Route() {

    @Override
    public Object handle(Request request, Response response) throws Exception {
		    //System.out.println("Lecture RepoList");
            List<String> repoContent=Files.readAllLines(new File("/var/equinox-loader-manager/local/repository.list").toPath())
            StringBuilder b=new StringBuilder();
            for(String s:repoContent)
            {
                    b.append(s.replace("/var/equinox-loader-manager",".")).append("<br>");
            }
            System.out.println(b.toString());
            return b.toString();
    }
});




defaultSparkService.get("/repository/remote/:name/add/:url",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().addRepository(request.params(":name"),request.params(":url"));
	}
});

defaultSparkService.get("/repository/remote/:name/delete",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().delRepository(request.params(":name"));
	}
});


defaultSparkService.get("/repository/remote/:name/fetch",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().fetchRemoteRepository(name);
	}
});

defaultSparkService.get("/repository/remote",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		try {
			return ManagerRmiClient.getInstance().getRemoteRegistry().fetchAllRemoteRepository();
		}
		catch (Throwable e) {
			System.out.println(e);
		}
		return null;
	}
});


defaultSparkService.get("/archive/:tar/:version/pull", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().pullTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});
defaultSparkService.get("/archive/:tar/:version/deploy",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getRemoteRegistry().deployTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});









