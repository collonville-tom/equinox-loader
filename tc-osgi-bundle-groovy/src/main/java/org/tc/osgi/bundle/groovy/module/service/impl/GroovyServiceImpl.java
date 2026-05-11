package org.tc.osgi.bundle.groovy.module.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.codehaus.groovy.control.CompilationFailedException;
import org.tc.osgi.bundle.groovy.interf.module.service.IGroovyService;
import org.tc.osgi.bundle.groovy.module.service.LoggerServiceProxy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * DroolsServiceImpl.java.
 * http://mrhaki.blogspot.com/2009/11/groovy-goodness-running-groovy-scripts.html
 * 
 * @author collonville thomas
 * @version
 * @track
 */
public class GroovyServiceImpl implements IGroovyService {

	private final GroovyClassLoader classLoader = new GroovyClassLoader();
	private static GroovyServiceImpl instance = null;

	public static GroovyServiceImpl getInstance() {
		if (GroovyServiceImpl.instance == null) {
			GroovyServiceImpl.instance = new GroovyServiceImpl();
		}
		return GroovyServiceImpl.instance;
	}

	private GroovyServiceImpl() {

	}

	// le binding c'est le contenu des parametres a pousser dans le script
	@Override
	public Optional<Object> executeSpecificScript(String path, Optional<Map<String, Object>> binding) {
		GroovyShell shell;
		if (binding.isPresent()) {
			Binding b = new Binding();
			for (String s : binding.get().keySet()) {
				Object v = binding.get().get(s);
				StringBuilder builder = new StringBuilder("Gestion du paramtre de script:");
				builder.append(s).append(",").append(v);
				LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class).debug(builder.toString());
				b.setProperty(s, v);
			}
			shell = new GroovyShell(b);
		} else {
			shell = new GroovyShell();
		}
		try {
			File f = new File(path);
			if (f.isFile()) {
				Script s = shell.parse(f);
				LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
						.debug("Execution du script:" + f.getName());
				return Optional.ofNullable(s.run());
			}

		} catch (CompilationFailedException | IOException e) {
			LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
					.error("Une erreur s'est produite lors de l'excution du script groovy " + path, e);
		}
		return Optional.empty();
	}

	@Override
	public void executeGroovyDirectory(String path, Optional<Map<String, Object>> binding) {

		File dir = new File(path);
		List<File> lsgroovyFile = new ArrayList<>();
		if (dir.isDirectory()) {
			File[] lsFile = dir.listFiles();
			for (File lsf : lsFile) {
				if (lsf.isFile()) {
					lsgroovyFile.add(lsf);
				}
			}
		}
		for (File f : lsgroovyFile) {
			this.executeSpecificScript(f.getAbsolutePath(), binding);
		}

	}

	public void loadGroovyDirectoryClassLib(String path) {
		File dir = new File(path);
		List<File> lsgroovyFile = new ArrayList<>();
		if (dir.isDirectory()) {
			File[] lsFile = dir.listFiles();
			for (File lsf : lsFile) {
				if (lsf.isFile()) {
					this.loadGroovyClassLib(lsf.getAbsolutePath());
				}
			}
		}
	}

	public void loadGroovyClassLib(String path) {
		try {
			LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class).debug("Chargement de la lib:" + path);
			this.classLoader.parseClass(new File(path));
		} catch (IOException e) {
			LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
			.error("Une erreur s'est produite lors du chargement de la lib groovy " + path, e);
		}
	}

	@Override
	public List<Class> getClassesFrom(Class type) {
		LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
				.debug("Finding classes derived of:" + type.getName());
		Class[] classes = this.classLoader.getLoadedClasses();
		List<Class> result = new ArrayList<>();
		for (Class c : classes) {
			if (c.getSuperclass().equals(type)) {
				LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
						.debug("Classes Found:" + c.getName());
				result.add(c);
			}
			for (Class ci : c.getInterfaces()) {
				if (ci.equals(type)) {
					LoggerServiceProxy.getInstance().getLogger(GroovyServiceImpl.class)
							.debug("Classes Found:" + c.getName());
					result.add(c);
				}
			}
		}
		return result;
	}

	@Override
	public ClassLoader getGroovyClassLoader() {
		return this.classLoader;
	}

}
