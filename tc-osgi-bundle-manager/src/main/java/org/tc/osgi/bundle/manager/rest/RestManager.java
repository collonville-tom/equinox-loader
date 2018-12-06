package org.tc.osgi.bundle.manager.rest;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.xml.ws.Endpoint;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.IXmlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.rpc.IRPCServer;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class RestManager implements IRPCServer{

	private String addr;
	private String port;
	
	@Override
	public void addObject(String signature, Object obj)
			throws RemoteException, MalformedURLException, UnknownHostException, FieldTrackingAssignementException {
        final StringBuilder b = new StringBuilder("http://");
        b.append(getAddr());
        b.append(":");
        b.append(getPort());
        b.append("/rs/");
        b.append(signature);
        LoggerGestionnary.getInstance(RestManager.class).debug("Deploiement du webservice : " + b.toString());
        Endpoint.publish(b.toString(), obj);
	}

	@Override
	public String getAddr() throws FieldTrackingAssignementException {
		if (addr == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(ManagerPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "addr");
        }
        return addr;
	}

	@Override
	public String getPort() throws FieldTrackingAssignementException {
		if (port == null) {
            final String xmlFile = ManagerPropertyFile.getInstance().getXMLFile();
            final IXmlProperty xmlFileObject = PropertyServiceProxy.getInstance().getXMLPropertyFile(xmlFile);
            xmlFileObject.fieldTraking(this, "port");
        }
        return port;
	}

	
	
}
