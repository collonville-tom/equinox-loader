package org.tc.osgi.bundle.manager.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.tc.osgi.bundle.manager.exception.TcManagerMBeanException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

public class EquinoxLoaderManager implements EquinoxLoaderManagerMBean{

	private MBeanServer server = ManagementFactory.getPlatformMBeanServer();
	
	public EquinoxLoaderManager() throws TcManagerMBeanException {

		this.registerMBean(this);
	}
	
	public <T> ObjectName buildObjectName(T object) throws TcManagerMBeanException 
	{
		Class<?> c=object.getClass();
		String objectName = c.getPackage().getName()+ ":type=" +c.getSimpleName();
		LoggerServiceProxy.getInstance().getLogger(EquinoxLoaderManager.class).debug("Creation ObjectName: "+objectName);
		try {
			return new ObjectName(objectName);
		} catch (MalformedObjectNameException e) {
			throw new TcManagerMBeanException("Erreur de construction de l'ObjectName "+object.getClass(),e);
		}
	}
	
	public <T> void registerMBean(T object) throws TcManagerMBeanException
	{
		try {
			server.registerMBean(object, this.buildObjectName(object));
		} catch (NotCompliantMBeanException|MBeanRegistrationException|InstanceAlreadyExistsException|TcManagerMBeanException e) {
			throw new TcManagerMBeanException("Impossible d enregistrer le bean "+object.getClass(),e);
		}
	}
}
