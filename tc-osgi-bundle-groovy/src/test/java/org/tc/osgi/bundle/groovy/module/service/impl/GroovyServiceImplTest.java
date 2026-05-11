package org.tc.osgi.bundle.groovy.module.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.tc.osgi.bundle.groovy.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

public class GroovyServiceImplTest {

	@Test
	public void test() {
		String path = "src/test/resources/test.groovy";

		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		GroovyServiceImpl.getInstance().loadGroovyClassLib(path);
		for (Class c : GroovyServiceImpl.getInstance().getClassesFrom(MonInterface.class)) {
			assertEquals("Sample", c.getName());
		}

	}

}
