package org.tc.osgi.bundle.utils.module.service.impl;

import org.tc.osgi.bundle.utils.conf.YamlPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.IYamlProperty;
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

	@Override
	public IYamlProperty getYamlPropertyFile(final String propertyFileName) throws FieldTrackingAssignementException {
		return YamlPropertyFile.getInstance(propertyFileName);
	}

}
