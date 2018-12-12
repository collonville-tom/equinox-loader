package org.tc.osgi.bundle.manager.core.internal.wrapper;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BundleHeaderWrapper {

	private static final String BUNDLE_NAME="Bundle-Name";
	
	private Map<String, String> properties = new HashMap<>();

	public BundleHeaderWrapper(Dictionary<String, String> headers) {

		this.buildProperties(headers);
	}

	private void buildProperties(Dictionary<String, String> headers) {
		Enumeration<String> it = headers.keys();
		for (Enumeration<String> e = headers.keys(); e.hasMoreElements();) {
			String key = e.nextElement();
			properties.put(key, headers.get(key));
		}
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
