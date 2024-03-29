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

defaultSparkService.get("/local/:name",new Route() {

                @Override
                public Object handle(Request request, Response response) throws Exception {
					    System.out.println("Lecture RepoList");
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

defaultSparkService.get("/rps-cmd",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		List<String> cmd=new ArrayList<String>();
		cmd.add("/rps-cmd -> cette liste");
		cmd.add("/fetchRemoteRepo -> initialise l'image local du contenu des repo distant");
		cmd.add("/fetchLocalRepo -> initialise l'image local du contenu du repo local");
		cmd.add("/pullTar/:tar/:version -> importe un tar depuis un repository distant et le depose dans le repository local");
		cmd.add("/deployTar/:tar/:version -> lance la procedure de deployement d'une archive tar dans le contexte d'installation de equinox (precede la phase d'installation du bundle contenu dans le tar");
		cmd.add("/pushTar/:tar/:version -> permet pour un client de l'interface REST de realiser l'extraction d'un TAR contenu dans le repo local");
		cmd.add("/addRepo/:name/:url -> ajout d'un repository, remplacer les / par des %2F par exemple: http:%2F%2Ftoto%2Ftata");
		cmd.add("/delRepo/:name -> suppression d'un repository, not implemented yet");
		return new JsonSerialiser().toJson(cmd);
	}
});

defaultSparkService.get("/addRepo/:name/:url",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().addRepo(request.params(":name"),request.params(":url"));
	}
});

defaultSparkService.get("/delRepo/:name",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().delRepo(request.params(":name"));
	}
});


defaultSparkService.get("/fetchRemoteRepo",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().fetchRemoteRepo();
	}
});
defaultSparkService.get("/fetchLocalRepo",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		try {
			return ManagerRmiClient.getInstance().getRemoteRegistry().fetchLocalRepo();
		}
	catch (Throwable e) {
		System.out.println(e);
	}
	return null;
}
});
defaultSparkService.get("/pullTar/:tar/:version", new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		return ManagerRmiClient.getInstance().getRemoteRegistry().pullTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});
defaultSparkService.get("/deployTar/:tar/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return ManagerRmiClient.getInstance().getRemoteRegistry().deployTar(request.params(TAR_TAG), request.params(VERSION_TAG));
	}
});
defaultSparkService.get("/pushTar/:tar/:version",new Route() {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String b=ManagerRmiClient.getInstance().getRemoteRegistry().pushTar(request.params(TAR_TAG), request.params(VERSION_TAG));
		response.redirect(b.toString());
		return "Redirection to " + b;
	}
});








