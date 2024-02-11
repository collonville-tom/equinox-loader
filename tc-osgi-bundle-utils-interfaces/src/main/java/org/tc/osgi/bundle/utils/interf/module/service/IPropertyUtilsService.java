package org.tc.osgi.bundle.utils.interf.module.service;

import org.tc.osgi.bundle.utils.interf.conf.IYamlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface IPropertyUtilsService {

	public IYamlProperty getYamlPropertyFile(String propertyFileName) throws FieldTrackingAssignementException;
}
