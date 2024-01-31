package org.tc.osgi.bundle.utils.module.service.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.tc.osgi.bundle.utils.interf.module.service.IUtilsService;
import org.tc.osgi.bundle.utils.interf.serial.ISerialTool;
import org.tc.osgi.bundle.utils.serial.SerialTool;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class UtilsServiceImpl implements IUtilsService {

	private static UtilsServiceImpl instance = null;

	public static UtilsServiceImpl getInstance() {
		if (UtilsServiceImpl.instance == null) {
			UtilsServiceImpl.instance = new UtilsServiceImpl();
		}
		return UtilsServiceImpl.instance;
	}

	/**
	 * @return SerialTool<Serializable>
	 *
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getSerialTool()
	 */
	@Override
	public <T extends Serializable> ISerialTool<T> getSerialTool() {
		return new SerialTool<T>();
	}

	public boolean contains(Annotation[] annots, Class<?> ann) {
		for (int i = 0; i < annots.length; i++) {
			if (ann.equals(annots[i].annotationType()))
				return true;
		}
		return false;
	}

	/**
	 * list2String.
	 *
	 * @param chaines
	 *            List<String>
	 * @return String
	 */
	public String list2String(final List chaines, final String delimiter) {
		final StringBuilder buff = new StringBuilder();
		final Iterator it = chaines.iterator();
		for (; it.hasNext();) {
			final Object o = it.next();
			buff.append(o.toString());
			if (chaines.indexOf(o) != (chaines.size() - 1)) {
				buff.append(delimiter);
			}
		}
		return buff.toString();
	}

	/**
	 * list2String.
	 * @param tab T[]
	 * @param delimiter String
	 * @return <T>
	 */
	public <T> String tab2String(final T[] tab, final String delimiter) {
		final StringBuilder buff = new StringBuilder();
		for (int i = 0; i < tab.length; i++) {

			buff.append(tab[i].toString());
			if (i != (tab.length - 1)) {
				buff.append(delimiter);
			}
		}
		return buff.toString();
	}

}
