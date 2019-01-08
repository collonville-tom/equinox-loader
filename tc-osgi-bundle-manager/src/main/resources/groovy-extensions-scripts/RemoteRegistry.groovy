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

import org.tc.osgi.bundle.manager.mbean.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.jmx.JMXInterface;

import spark.Route;
import spark.Response;
import spark.Spark;
import spark.Request;


String TAR_TAG = ":tar";
String VERSION_TAG = ":version";

Spark.get("/fetchRemoteRepo",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getRemoteRegistry().fetchRemoteRepo();
			}
		}); 
Spark.get("/updateLocal",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getRemoteRegistry().updateLocal();
			}
		});  
Spark.get("/pullOnRemoteRepo/:tar/:version", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getRemoteRegistry().pullOnRemoteRepo(request.params(TAR_TAG), request.params(VERSION_TAG));
			}
		});
Spark.get("/deploy/:tar/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return JMXInterface.getInstance().getRemoteRegistry().installBundle(request.params(TAR_TAG), request.params(VERSION_TAG));
			}
		});
Spark.get("/pullTar/:tar/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				String b=JMXInterface.getInstance().getRemoteRegistry().pushTar(request.params(TAR_TAG), request.params(VERSION_TAG));
				response.redirect(b.toString());
				return "Redirection to " + b;
			}
		}); 







