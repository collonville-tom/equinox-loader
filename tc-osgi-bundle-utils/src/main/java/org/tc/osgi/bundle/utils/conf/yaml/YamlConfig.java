package org.tc.osgi.bundle.utils.conf.yaml;

import java.util.List;

import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public class YamlConfig {

	private List<ConfigElement> config;

	public List<ConfigElement> getConfig() {
		return config;
	}

	public void setConfig(List<ConfigElement> config) {
		this.config = config;
	}

	public ConfigElement getElement(final String _declaredField, final Class<? extends Object> class1) throws FieldTrackingAssignementException {
		for (final ConfigElement element : config) {
			if (class1.getCanonicalName().equals(element.getClassName()) && element.getField().equals(_declaredField)) {
				return element;
			}
		}
		throw new FieldTrackingAssignementException("Le champs " + class1.getCanonicalName() + ":" + _declaredField + " n'existe pas");
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("ConfigurationLibrary:");
		for (final ConfigElement e : config) {
			buff.append(e);
			buff.append("\n");
		}

		return buff.toString();
	}
}
