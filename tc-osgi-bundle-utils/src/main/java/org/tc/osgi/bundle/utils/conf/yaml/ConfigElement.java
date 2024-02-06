package org.tc.osgi.bundle.utils.conf.yaml;

import java.lang.reflect.Field;

public class ConfigElement {

	private String value;
	private String type;
	private String field;
	private String className;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean checkClass(final Object _obj) {
		if (_obj.getClass().getCanonicalName().equals(className)) {
			return true;
		}
		return false;

	}

	public boolean checkType(final Object _obj) throws NoSuchFieldException, SecurityException {
		final Field field = _obj.getClass().getDeclaredField(this.field);
		if (field.getType().getCanonicalName().equals(this.type)) {
			return true;
		}
		return false;
	}

	public String toString() {
		final StringBuilder buff = new StringBuilder("ConfigElement[");
		buff.append("defaultValue:").append(value).append(",");
		buff.append("valueType:").append(type).append(",");
		buff.append("fieldName:").append(field).append(",");
		buff.append("className:").append(className).append(",");
		buff.append("]");
		return buff.toString();
	}
}
