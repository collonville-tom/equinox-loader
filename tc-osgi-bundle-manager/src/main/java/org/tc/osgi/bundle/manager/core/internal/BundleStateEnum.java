package org.tc.osgi.bundle.manager.core.internal;

public enum BundleStateEnum {
	UNINSTALLED, INSTALLED, RESOLVED, STARTING, STOPPING, ACTIVE,UNKNOW;


	public static BundleStateEnum detect(int value) {
		switch (value) {
		case 1:
			return UNINSTALLED;
		case 2:
			return INSTALLED;
		case 4:
			return RESOLVED;
		case 8:
			return STARTING;
		case 10:
			return STOPPING;
		case 32:
			return ACTIVE;
		default: return UNKNOW;
		}
	}

}
