package org.tc.osgi.bundle.manager.groovy;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.spark.module.service.impl.SparkServiceImpl;
import java.lang.Exception;
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
				return this.fetchRemoteRepo();
			}
		}); 
Spark.get("/updateLocal",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.updateLocal();
			}
		});  
Spark.get("/pullOnRemoteRepo/:tar/:version", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.pullOnRemoteRepo(request.params(TAR_TAG), request.params(VERSION_TAG));
			}
		});
Spark.get("/deploy/:tar/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return this.installBundle(request.params(TAR_TAG), request.params(VERSION_TAG));
			}
		});
Spark.get("/pullTar/:tar/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				String b=this.pushTar(request.params(TAR_TAG), request.params(VERSION_TAG));
				response.redirect(b.toString());
				return "Redirection to " + b;
			}
		}); 







