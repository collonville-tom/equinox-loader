package org.tc.osgi.bundle.manager.core.bundle;

public class TarGzBundle {

	private String name;
	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url;

	public TarGzBundle(String name, String version, String url) {
		this.name = name;
		this.url = url;
		this.version = version;
	}

	public String toString() {
		StringBuilder b = new StringBuilder(this.name);
		b.append("(").append(this.version).append("):");
		return b.append(this.url).toString();
	}
}
