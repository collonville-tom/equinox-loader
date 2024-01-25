package org.tc.osgi.bundle.utils.interf.serial;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * SerialTool.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_040
 */
public interface ISerialTool<T extends Serializable> {

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
	public T read(final String name, final String directory) throws FileNotFoundException;

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
	public void save(final T object, final String name, final String directory) throws FileNotFoundException;

}
