package org.tc.osgi.bundle.utils.interf.collection;

/**
 * IPredicate.java.
 *
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_010
 */
public interface IPredicate<T> {
	/**
	 * evaluate.
	 *
	 * @param e
	 *            T
	 * @return boolean
	 */
	boolean evaluate(T e);
}
