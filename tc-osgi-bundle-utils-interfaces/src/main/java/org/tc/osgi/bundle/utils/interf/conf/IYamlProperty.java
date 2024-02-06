package org.tc.osgi.bundle.utils.interf.conf;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public interface IYamlProperty {

	public void fieldTraking(final Object _obj, final String _declaredField) throws FieldTrackingAssignementException;
}
