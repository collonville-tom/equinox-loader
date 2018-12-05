package org.tc.osgi.bundle.manager.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/manager")
public class RestManager {

	
	@GET
	public String getXml() {
	    return "<bonjour>Bonjour ENSMA</bonjour>";
	}
}
