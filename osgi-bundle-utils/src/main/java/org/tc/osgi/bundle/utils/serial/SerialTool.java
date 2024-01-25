package org.tc.osgi.bundle.utils.serial;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import org.tc.osgi.bundle.utils.interf.serial.ISerialTool;

/**
 * SerialTool.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_040
 */
public class SerialTool<T extends Serializable> implements ISerialTool<T> {

	/**
	 * SerialTool constructor.
	 */
	public SerialTool() {
		super();
	}

	/**
	 * read.
	 *
	 * @param name
	 *            String
	 * @param directory
	 *            String
	 * @return Object
	 * @throws FileNotFoundException
	 */
	public T read(final String name, final String directory) throws FileNotFoundException {

		XMLDecoder decoder;
		decoder = new XMLDecoder(new FileInputStream(directory + name));
		final T object = (T) decoder.readObject();
		decoder.close();
		return object;

	}

	/**
	 * save.
	 *
	 * @param object
	 *            Object
	 * @param name
	 *            String
	 * @param directory
	 *            String
	 * @throws FileNotFoundException
	 */
	public void save(final T object, final String name, final String directory) throws FileNotFoundException {
		XMLEncoder e;
		e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(directory + name)));
		e.writeObject(object);
		e.close();

	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SerialTools class";
	}

}
