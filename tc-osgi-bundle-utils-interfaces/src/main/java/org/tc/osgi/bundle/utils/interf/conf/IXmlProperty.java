package org.tc.osgi.bundle.utils.interf.conf;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public interface IXmlProperty {

	public void fieldTraking(final Object _obj, final String _declaredField) throws FieldTrackingAssignementException;
}
