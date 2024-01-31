package org.tc.osgi.bundle.utils.interf.module.service;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

import org.tc.osgi.bundle.utils.interf.serial.ISerialTool;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface IUtilsService {

	/**
	 * getSerialTool.
	 *
	 * @return SerialTool<Serializable>
	 */
	public <T extends Serializable> ISerialTool<T> getSerialTool();

	public boolean contains(Annotation[] annots, Class<?> ann);

	/**
	 * list2String.
	 *
	 * @param chaines
	 *            List<String>
	 * @return String
	 */
	public String list2String(final List chaines, final String delimiter);

	/**
	 * list2String.
	 * @param tab T[]
	 * @param delimiter String
	 * @return <T>
	 */
	public <T> String tab2String(final T[] tab, final String delimiter);

}
