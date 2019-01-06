package org.tc.osgi.bundle.manager.groovy;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.spark.module.service.impl.SparkServiceImpl;
import java.lang.Exception;
import spark.Route;
import spark.Response;
import spark.Spark;
import spark.Request;

Spark.get("/bundles",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleList();
			}
		}); 
Spark.get("/bundles/short",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleShortList();
			}
		});  
Spark.get("/services", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleServices();
			}
		});
Spark.get("/bundle/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleInfo(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/start/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return this.bundleStart(request.params(":bundleName"),request.params(":version"));
			}
		}); 
Spark.get("/stop/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return this.bundleStop(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/uninstall/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return this.bundleUninstall(request.params(":bundleName"),request.params(":version"));
			}
		}); 
Spark.get("/install/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return this.bundleInstall(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/service/:serviceName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleService(request.params(":serviceName"),request.params(":version"));
			}
		});
Spark.get("/depends/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return this.bundleDependencies(request.params(":bundleName"),request.params(":version"));
			}
		});

		

