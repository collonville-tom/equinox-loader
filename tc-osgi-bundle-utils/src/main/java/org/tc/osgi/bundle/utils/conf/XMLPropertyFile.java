package org.tc.osgi.bundle.utils.conf;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.tc.osgi.bundle.utils.conf.jaxb.ConfigurationLibrary;
import org.tc.osgi.bundle.utils.conf.jaxb.ElementConfiguration;
import org.tc.osgi.bundle.utils.conf.jaxb.ObjectFactory;
import org.tc.osgi.bundle.utils.interf.conf.IXmlProperty;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * PropertyFile.java.
 *
 * @author collonville thomas
 * @version 0.0.9
 * @track SDD_BUNDLE_UTILS_025
 */
public class XMLPropertyFile implements IXmlProperty {

	/**
	 * Map<String,PropertyFile> propertyFileInstances.
	 */
	private static Map<String, IXmlProperty> propertyFileInstances = new HashMap<String, IXmlProperty>();

	/**
	 * getInstance.
	 *
	 * @param _name PropertyFile
	 * @throws JAXBException
	 * @returnString
	 */
	public static IXmlProperty getInstance(final String _name) throws FieldTrackingAssignementException {
		if (!XMLPropertyFile.propertyFileInstances.containsKey(_name)) {
			XMLPropertyFile.propertyFileInstances.put(_name, new XMLPropertyFile(_name));
		}
		return XMLPropertyFile.propertyFileInstances.get(_name);
	}

	/**
	 * Getter propertyFileInstances.
	 * 
	 * @return
	 */
	public static Map<String, IXmlProperty> getPropertyFileInstances() {
		return XMLPropertyFile.propertyFileInstances;
	}

	/**
	 * ConfigurationLibrary bibliotheque.
	 */
	private ConfigurationLibrary bibliotheque = null;

	/**
	 * String configFile.
	 */
	private final String configFile;

	/**
	 * PropertyFile constructor.
	 *
	 * @throws JAXBException
	 */
	protected XMLPropertyFile(final String _name) throws FieldTrackingAssignementException {
		configFile = _name;
		loadXmlFile(_name);
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
			final ElementConfiguration element = bibliotheque.getElement(_declaredField, _obj.getClass());
			if (element.checkType(_obj)) {
				field.set(_obj, element.getDefaultValue());
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
	public ConfigurationLibrary getBibliotheque() {
		return bibliotheque;
	}

	/**
	 * loadXmlFile.
	 * 
	 * @param fileName String
	 * @throws FieldTrackingAssignementException
	 */
	protected void loadXmlFile(final String fileName) throws FieldTrackingAssignementException {
		try {
			LoggerGestionnary.getInstance(this.getClass()).debug("Chargement fichier " + fileName + UtilsPropertyFile.getInstance().getXmlPropertiesExtension());

			// http://www.java-forums.org/new-java/58427-not-sure-what-error-means-javax-xml-bind-javaxbexception.html
			final ObjectFactory objectFactory = new ObjectFactory();
			final JAXBContext context = JAXBContext.newInstance(objectFactory.getClass().getPackage().getName(), ObjectFactory.class.getClassLoader());

			final Unmarshaller unmarshaller = context.createUnmarshaller();

			File file2load = new File(fileName + UtilsPropertyFile.getInstance().getXmlPropertiesExtension());
			if (!file2load.exists()) {
				LoggerGestionnary.getInstance(this.getClass())
						.debug("Le fichier resources " + file2load.getName() + " n'existe pas, tentative dans le repertoire src/main/resources");
				file2load = new File("src/main/resources/" + fileName + UtilsPropertyFile.getInstance().getXmlPropertiesExtension());
			}

			bibliotheque = (ConfigurationLibrary) unmarshaller.unmarshal(file2load);
			LoggerGestionnary.getInstance(this.getClass()).debug("Chargement lib " + bibliotheque.toString());
		} catch (final JAXBException e) {
			throw new FieldTrackingAssignementException(e.getMessage(), e);
		}

	}

	/**
	 * reloadXmlFile.
	 * 
	 * @throws FieldTrackingAssignementException
	 */
	public void reloadXmlFile() throws FieldTrackingAssignementException {
		loadXmlFile(configFile);
	}

	/**
	 * reloadXmlFile.
	 * 
	 * @param fileName String
	 * @throws FieldTrackingAssignementException
	 */
	public void reloadXmlFile(final String fileName) throws FieldTrackingAssignementException {
		loadXmlFile(fileName);
	}

	/**
	 * Setter bibliotheque.
	 * 
	 * @param bibliotheque ConfigurationLibrary
	 */
	public void setBibliotheque(final ConfigurationLibrary bibliotheque) {
		this.bibliotheque = bibliotheque;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("XMLPropertyFile-list(configFile):\n");
		buff.append(bibliotheque);
		return buff.toString();
	}
}
