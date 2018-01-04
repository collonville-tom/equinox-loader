package org.tc.osgi.bundle.utils.interf.module.service;

import org.tc.osgi.bundle.utils.interf.conf.IXmlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface IPropertyUtilsService {

	/**
	 * getPropertyFile.
	 *
	 * @param propertyFileName
	 *            String
	 * @return PropertyFile
	 * @throws FieldTrackingAssignementException
	 */
	public IXmlProperty getXMLPropertyFile(String propertyFileName) throws FieldTrackingAssignementException;

}
