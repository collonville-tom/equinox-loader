package org.tc.osgi.bundle.manager.jmx;

import java.io.IOException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.mbean.EquinoxRegistryMBean;
import org.tc.osgi.bundle.manager.mbean.RemoteRegistryMBean;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public class JMXInterface {

	private JMXServiceURL url;
	private JMXConnector jmxc;
	private MBeanServerConnection mbsc;
	
	private ObjectName mEquinoxRegistry;
	private ObjectName mRemoteRegistry;
	
	private EquinoxRegistryMBean equinoxRegistryMBean;
	private RemoteRegistryMBean remoteRegistryMBean;
	private String jmxPort;
	

	private static JMXInterface instance;
	
	public static JMXInterface getInstance() throws IOException, MalformedObjectNameException, FieldTrackingAssignementException
	{
		if(instance==null)
			instance=new JMXInterface();
		return instance;
		
	}
	
	private JMXInterface() throws IOException, MalformedObjectNameException, FieldTrackingAssignementException {
		LoggerServiceProxy.getInstance().getLogger(JMXInterface.class).debug("Creation du client JMX");
		this.url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:"+this.getJmxPort()+"/jmxrmi");
		this.jmxc = JMXConnectorFactory.connect(url, null);
		this.mbsc = jmxc.getMBeanServerConnection();
		//TODO a corriger
		this.mEquinoxRegistry = new ObjectName("org.tc.osgi.bundle.manager.mbean:type=EquinoxRegistry");
		this.mRemoteRegistry = new ObjectName("org.tc.osgi.bundle.manager.mbean:type=RemoteRegistry");
		this.equinoxRegistryMBean=JMX.newMBeanProxy(mbsc, mEquinoxRegistry, EquinoxRegistryMBean.class, true);
		this.remoteRegistryMBean=JMX.newMBeanProxy(mbsc, mRemoteRegistry, RemoteRegistryMBean.class, true);
	}
	

	
	public String getJmxPort() throws FieldTrackingAssignementException  {
        if (this.jmxPort == null) {
        	PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "jmxPort");
        }
        return this.jmxPort;
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
