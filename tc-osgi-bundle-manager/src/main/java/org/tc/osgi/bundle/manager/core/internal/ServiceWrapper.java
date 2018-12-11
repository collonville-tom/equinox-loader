package org.tc.osgi.bundle.manager.core.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class ServiceWrapper {

	private List<BundleWrapper> bWrapper=new ArrayList<>();
	private List<String> serviceProperties=new ArrayList<>();
	
	public ServiceWrapper(ServiceReference<?> service) {
		
		for(String s:service.getPropertyKeys())
		{
			LoggerGestionnary.getInstance(ServiceWrapper.class).debug("analyse des propriété du service "+s);
			this.serviceProperties.add(s);
		}
		for(Bundle b:service.getUsingBundles())
		{
			LoggerGestionnary.getInstance(ServiceWrapper.class).debug("analyse des bundles associés au service: "+b.getSymbolicName());
			bWrapper.add(new BundleWrapper(b));
		}
				
	}
	
	public List<BundleWrapper> getbWrapper() {
		return bWrapper;
	}

	public void setbWrapper(List<BundleWrapper> bWrapper) {
		this.bWrapper = bWrapper;
	}

	public List<String> getServiceProperties() {
		return serviceProperties;
	}

	public void setServiceProperties(List<String> serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

}
