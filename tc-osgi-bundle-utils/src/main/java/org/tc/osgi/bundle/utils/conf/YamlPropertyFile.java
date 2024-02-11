package org.tc.osgi.bundle.utils.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.tc.osgi.bundle.utils.conf.yaml.ConfigElement;
import org.tc.osgi.bundle.utils.conf.yaml.YamlConfig;
import org.tc.osgi.bundle.utils.interf.conf.IYamlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * PropertyFile.java.
 *
 * @author collonville thomas
 * @version 0.0.9
 * @track SDD_BUNDLE_UTILS_025
 */
public class YamlPropertyFile implements IYamlProperty {

	/**
	 * Map<String,PropertyFile> propertyFileInstances.
	 */
	private static Map<String, IYamlProperty> propertyFileInstances = new HashMap<String, IYamlProperty>();

	public static IYamlProperty getInstance(final String _name) throws FieldTrackingAssignementException {
		if (!YamlPropertyFile.propertyFileInstances.containsKey(_name)) {
			YamlPropertyFile.propertyFileInstances.put(_name, new YamlPropertyFile(_name));
		}
		return YamlPropertyFile.propertyFileInstances.get(_name);
	}

	/**
	 * Getter propertyFileInstances.
	 * 
	 * @return
	 */
	public static Map<String, IYamlProperty> getPropertyFileInstances() {
		return YamlPropertyFile.propertyFileInstances;
	}

	/**
	 * YamlConfig bibliotheque.
	 */
	private YamlConfig bibliotheque = null;

	/**
	 * String configFile.
	 */
	private final String configFile;

	protected YamlPropertyFile(final String _name) throws FieldTrackingAssignementException {
		configFile = _name;
		loadYamlFile(_name);
	}

	/**
	 * fieldTraking.
	 *
	 * @param _obj           Object
	 * @param _declaredField Field
	 * @throws FieldTrackingAssignementException
	 */
	public void fieldTraking(final Object _obj, final String _declaredField) throws FieldTrackingAssignementException {

		try {
			final Field field = _obj.getClass().getDeclaredField(_declaredField);
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			LoggerGestionnary.getInstance(this.getClass())
					.debug("Recuperation de la valeur pour l'object de class " + _obj.getClass().getCanonicalName() + " pour le champs " + _declaredField);
			final ConfigElement element = bibliotheque.getElement(_declaredField, _obj.getClass());
			if (element.checkType(_obj)) {
				field.set(_obj, element.getValue());
			} else {
				throw new FieldTrackingAssignementException("Le type declaré de l'element " + element + " est incoherent");
			}
		} catch (final ReflectiveOperationException e) {
			throw new FieldTrackingAssignementException(e.getMessage(), e);
		} catch (final SecurityException e) {
			throw new FieldTrackingAssignementException(e.getMessage(), e);
		} catch (final IllegalArgumentException e) {
			throw new FieldTrackingAssignementException(e.getMessage(), e);
		}

	}

	/**
	 * Getter bibliotheque.
	 * 
	 * @return
	 */
	public YamlConfig getBibliotheque() {
		return bibliotheque;
	}

	/**
	 * loadXmlFile.
	 * 
	 * @param fileName String
	 * @throws FieldTrackingAssignementException
	 */
	protected void loadYamlFile(final String fileName) throws FieldTrackingAssignementException {
		LoggerGestionnary.getInstance(this.getClass()).debug("Chargement fichier " + fileName + UtilsPropertyFile.getInstance().getYamlPropertiesExtension());

		Yaml yaml = new Yaml(new Constructor(YamlConfig.class, new LoaderOptions()));
		try {
			InputStream inputStream = new FileInputStream(new File(fileName + UtilsPropertyFile.getInstance().getYamlPropertiesExtension()));
			this.bibliotheque = yaml.load(inputStream);
		} catch (FileNotFoundException e) {
			LoggerGestionnary.getInstance(this.getClass()).error("Error loading with full path " + fileName + UtilsPropertyFile.getInstance().getYamlPropertiesExtension(), e);
			throw new FieldTrackingAssignementException("Error loading with full path", e);
		}
		LoggerGestionnary.getInstance(this.getClass()).debug("Chargement lib " + bibliotheque.toString());

	}

	/**
	 * reloadXmlFile.
	 * 
	 * @throws FieldTrackingAssignementException
	 */
	public void reloadYamlFile() throws FieldTrackingAssignementException {
		loadYamlFile(configFile);
	}

	/**
	 * reloadXmlFile.
	 * 
	 * @param fileName String
	 * @throws FieldTrackingAssignementException
	 */
	public void reloadYamlFile(final String fileName) throws FieldTrackingAssignementException {
		loadYamlFile(fileName);
	}

	/**
	 * Setter bibliotheque.
	 * 
	 * @param bibliotheque YamlConfig
	 */
	public void setBibliotheque(final YamlConfig bibliotheque) {
		this.bibliotheque = bibliotheque;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("YamlPropertyFile-list(configFile):\n");
		buff.append(bibliotheque);
		return buff.toString();
	}
}
