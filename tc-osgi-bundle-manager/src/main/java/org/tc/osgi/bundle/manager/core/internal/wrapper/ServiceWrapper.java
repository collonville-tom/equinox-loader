package org.tc.osgi.bundle.manager.core.internal.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class ServiceWrapper {

	private Map<String,Object> serviceProperties = new HashMap<>();
	private List<BundleWrapper> bWrapper = new ArrayList<>();
	
	

	public ServiceWrapper(ServiceReference<?> service) {

		String[] ss = service.getPropertyKeys();
		if (ss != null) {
			for (String s : ss) {
				//LoggerGestionnary.getInstance(ServiceWrapper.class).debug("analyse des propriété du service " + s);
				this.serviceProperties.put(s,service.getProperty(s));
			}
		}
		Bundle[] bs = service.getUsingBundles();
		if (bs != null) {
			for (Bundle b : bs) {
				//LoggerGestionnary.getInstance(ServiceWrapper.class).debug("analyse des bundles associés au service: " + b.getSymbolicName());
				bWrapper.add(new BundleWrapper(b));
			}
		}

	}

	public List<BundleWrapper> getbWrapper() {
		return bWrapper;
	}

	public void setbWrapper(List<BundleWrapper> bWrapper) {
		this.bWrapper = bWrapper;
	}

	public Map<String, Object> getServiceProperties() {
		return serviceProperties;
	}

	public void setServiceProperties(Map<String, Object> serviceProperties) {
		this.serviceProperties = serviceProperties;
	}



}
