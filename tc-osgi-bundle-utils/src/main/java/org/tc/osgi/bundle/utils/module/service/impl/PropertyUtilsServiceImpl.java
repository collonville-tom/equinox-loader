package org.tc.osgi.bundle.utils.module.service.impl;

import javax.xml.bind.JAXBException;

import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.IXmlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class PropertyUtilsServiceImpl implements IPropertyUtilsService {

	/**
	 * @param propertyFileName
	 *            String
	 * @return PropertyFile
	 * @throws JAXBException
	 *
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getXMLPropertyFile(java.lang.String)
	 */
	@Override
	public IXmlProperty getXMLPropertyFile(final String propertyFileName) throws FieldTrackingAssignementException {
		return XMLPropertyFile.getInstance(propertyFileName);
	}

}
