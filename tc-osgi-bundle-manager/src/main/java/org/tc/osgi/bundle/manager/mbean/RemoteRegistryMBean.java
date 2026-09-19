package org.tc.osgi.bundle.manager.mbean;

import java.io.Serializable;
import java.rmi.Remote;

public interface RemoteRegistryMBean extends Remote, Serializable {

	public String extractTar(String bundleName, String version);

	public byte[] serveTar(String name, String version) throws java.io.FileNotFoundException;

	public String removeTar(String name, String version) throws java.io.FileNotFoundException;

	public String receiveTar(String tarname, String version, byte[] body);

	public String collectTar(String name, String version);

	public String fetchRepo();

	public String addRepo(String name, String url);

	public String delRepo(String name);

}
