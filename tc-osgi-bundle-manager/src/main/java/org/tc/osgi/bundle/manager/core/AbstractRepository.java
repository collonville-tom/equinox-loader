package org.tc.osgi.bundle.manager.core;

public abstract class AbstractRepository {

	
	public abstract void fetch();
	public abstract void pull(String bundle);
}
