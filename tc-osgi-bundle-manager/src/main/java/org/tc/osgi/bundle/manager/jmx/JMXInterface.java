package org.tc.osgi.bundle.manager.jmx;

import java.io.IOException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.tc.osgi.bundle.manager.core.external.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.core.internal.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;

public class JMXInterface {

	private JMXServiceURL url;
	private JMXConnector jmxc;
	private MBeanServerConnection mbsc;
	
	private ObjectName mEquinoxRegistry;
	private ObjectName mRemoteRegistry;
	
	private EquinoxRegistryMBean equinoxRegistryMBean;
	private RemoteRegistryMBean remoteRegistryMBean;
	

	private static JMXInterface instance;
	
	public static JMXInterface getInstance() throws IOException, MalformedObjectNameException
	{
		if(instance==null)
			instance=new JMXInterface();
		return instance;
		
	}
	
	private JMXInterface() throws IOException, MalformedObjectNameException {
		LoggerServiceProxy.getInstance().getLogger(JMXInterface.class).debug("Creation du client JMX");
		this.url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:7001/jmxrmi");
		this.jmxc = JMXConnectorFactory.connect(url, null);
		this.mbsc = jmxc.getMBeanServerConnection();
		this.mEquinoxRegistry = new ObjectName("org.tc.osgi.bundle.manager.core.internal:type=EquinoxRegistry");
		this.mRemoteRegistry = new ObjectName("org.tc.osgi.bundle.manager.core.external:type=RemoteRegistry");
		this.equinoxRegistryMBean=JMX.newMBeanProxy(mbsc, mEquinoxRegistry, EquinoxRegistryMBean.class, true);
		this.remoteRegistryMBean=JMX.newMBeanProxy(mbsc, mRemoteRegistry, RemoteRegistryMBean.class, true);
	}
	
	public EquinoxRegistryMBean getEquinoxRegistry()
	{
		LoggerServiceProxy.getInstance().getLogger(JMXInterface.class).debug("Get equinoxRegistryMBean");
		return this.equinoxRegistryMBean;
	}
	
	public RemoteRegistryMBean getRemoteRegistry()
	{	
		LoggerServiceProxy.getInstance().getLogger(JMXInterface.class).debug("Get remoteRegistryMBean");
		return this.remoteRegistryMBean;
	}
}
