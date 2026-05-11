package org.tc.osgi.bundle.groovy.interf.module.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;



/**
 * IAptIoService.java.
 * @author collonville thomas
 * @version
 * @track
 */
public interface IGroovyService {

	public Optional<Object> executeSpecificScript(String path,Optional<Map<String,Object>> binding);
	public void executeGroovyDirectory(String path,Optional<Map<String,Object>> binding);
	
	public void loadGroovyDirectoryClassLib(String path) ;
	public void loadGroovyClassLib(String path);
	
	public ClassLoader getGroovyClassLoader();
	public List<Class> getClassesFrom(Class type);
}
