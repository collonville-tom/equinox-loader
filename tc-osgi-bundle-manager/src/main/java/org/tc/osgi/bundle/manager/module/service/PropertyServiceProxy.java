package org.tc.osgi.bundle.manager.module.service;

import org.tc.osgi.bundle.utils.interf.conf.IYamlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;

/**
 * UtilsServiceProxy.java.
 * 
 * @author collonville thomas
 * @version
 * @track
 */
public class PropertyServiceProxy implements IPropertyUtilsService {

	/**
	 * UtilsServiceProxy instance.
	 */
	private static PropertyServiceProxy instance = null;

	/**
	 * getInstance.
	 * 
	 * @return UtilsServiceProxy
	 */
	public static PropertyServiceProxy getInstance() {
		if (PropertyServiceProxy.instance == null) {
			PropertyServiceProxy.instance = new PropertyServiceProxy();
		}
		return PropertyServiceProxy.instance;
	}

	/**
	 * IUtilsService service.
	 */
	private IPropertyUtilsService service = null;

	/**
	 * UtilsServiceProxy constructor.
	 */
	private PropertyServiceProxy() {

	}

	/**
	 * getService.
	 * 
	 * @return IUtilsService
	 */
	public IPropertyUtilsService getService() {
		return service;
	}

	/**
	 * setService.
	 * 
	 * @param service IUtilsService
	 */
	public void setService(final IPropertyUtilsService service) {
		this.service = service;
	}

//	@Override
//	public IXmlProperty getXMLPropertyFile(String propertyFileName) throws FieldTrackingAssignementException {
//		return service.getXMLPropertyFile(propertyFileName);
//	}

	@Override
	public IYamlProperty getYamlPropertyFile(String propertyFileName) throws FieldTrackingAssignementException {
		return service.getYamlPropertyFile(propertyFileName);
	}
}
