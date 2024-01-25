package org.tc.osgi.bundle.manager.core.wrapper;

import org.osgi.framework.Bundle;

public class BundleWrapperShortDescription {


	private String bundleDetails;

	public BundleWrapperShortDescription(Bundle bundle)
	{
		this.wrap(bundle);
	}

	private void wrap(Bundle bundle) {
		StringBuilder b=new StringBuilder("(");
		b.append(bundle.getBundleId()).append(":");
		b.append(BundleStateEnum.detect(bundle.getState()).toString()).append(":");
		b.append(bundle.getSymbolicName()).append(")");
		this.bundleDetails=b.toString();
	}

	
	public String getBundleDetails() {
		return bundleDetails;
	}

	public void setBundleDetails(String bundleDetails) {
		this.bundleDetails = bundleDetails;
	}


}
