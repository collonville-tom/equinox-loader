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
import org.tc.osgi.bundle.manager.jmx.JMXInterface;

import spark.Route;
import spark.Response;
import spark.Spark;
import spark.Request;


Spark.get("/bundles",new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                    response.type("application/json");
                    System.out.println(JMXInterface.getInstance().getEquinoxRegistry().bundleList());
                    return JMXInterface.getInstance().getEquinoxRegistry().bundleList();
            }
        });
Spark.get("/bundles/short",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getEquinoxRegistry().bundleShortList();
			}
		});  
Spark.get("/services", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getEquinoxRegistry().bundleServices();
			}
		});
Spark.get("/bundle/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getEquinoxRegistry().bundleInfo(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/start/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return JMXInterface.getInstance().getEquinoxRegistry().bundleStart(request.params(":bundleName"),request.params(":version"));
			}
		}); 
Spark.get("/stop/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return JMXInterface.getInstance().getEquinoxRegistry().bundleStop(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/uninstall/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return JMXInterface.getInstance().getEquinoxRegistry().bundleUninstall(request.params(":bundleName"),request.params(":version"));
			}
		}); 
Spark.get("/install/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				return JMXInterface.getInstance().getEquinoxRegistry().bundleInstall(request.params(":bundleName"),request.params(":version"));
			}
		});
Spark.get("/service/:serviceName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getEquinoxRegistry().bundleService(request.params(":serviceName"),request.params(":version"));
			}
		});
Spark.get("/depends/:bundleName/:version",new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				response.type("application/json");
				return JMXInterface.getInstance().getEquinoxRegistry().bundleDependencies(request.params(":bundleName"),request.params(":version"));
			}
		});



