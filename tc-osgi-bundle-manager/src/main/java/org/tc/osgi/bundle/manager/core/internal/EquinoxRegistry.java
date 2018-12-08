package org.tc.osgi.bundle.manager.core.internal;

import org.tc.osgi.bundle.manager.core.AbstractRegistry;

import spark.Spark;


// classe qui permet d'acceder au differents ffonctionnalité de manipulation des bundles, install, start, stop, remove
public class EquinoxRegistry extends AbstractRegistry {

	@Override
	public void buildRegistryCmd() {
		Spark.get("/bundles", (request, response) -> this.bundleList());//liste les bundles et leur etat (comme a la console)
		Spark.get("/bundle", (request, response) -> this.bundleList());//info sur un bundle
		Spark.get("/start", (request, response) -> this.bundleList());//demarrage d'un bundle
		Spark.get("/stop", (request, response) -> this.bundleList());// arret d'un bundle
		Spark.get("/uninstall", (request, response) -> this.bundleList());// desinstallation
		Spark.get("/install", (request, response) -> this.bundleList());// installation
		Spark.get("/services", (request, response) -> this.bundleList());// liste des services
		Spark.get("/service", (request, response) -> this.bundleList());// details d'un services
		
		
	}

	private String bundleList() {
		// TODO Auto-generated method stub
		return null;
	}

}
