package org.tc.osgi.bundle.manager.core.internal.wrapper;

import org.osgi.framework.Bundle;

public class BundleWrapper {

	private long bundleId;
	private String symbolicName;
	private int state;
	private String stateSignification;
	private String location;
	private int majorVersion;
	private int minorVersion;
	

	public BundleWrapper(Bundle bundle)
	{
		this.wrap(bundle);
	}

	private void wrap(Bundle bundle) {
		this.bundleId=bundle.getBundleId();
		this.symbolicName=bundle.getSymbolicName();
		this.state=bundle.getState();
		this.stateSignification=BundleStateEnum.detect(this.state).toString();
		this.location=bundle.getLocation();
		this.majorVersion=bundle.getVersion().getMajor();
		this.minorVersion=bundle.getVersion().getMinor();
	}

	

	public String getStateSignification() {
		return stateSignification;
	}

	public void setStateSignification(String stateSignification) {
		this.stateSignification = stateSignification;
	}

	public long getBundleId() {
		return bundleId;
	}

	public void setBundleId(long bundleId) {
		this.bundleId = bundleId;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}
	
}
