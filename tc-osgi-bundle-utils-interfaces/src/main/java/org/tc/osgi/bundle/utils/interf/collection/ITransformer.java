package org.tc.osgi.bundle.utils.interf.collection;

import java.util.Collection;

/**
 * ITransformer.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_010
 */
public interface ITransformer<T> {
	/**
	 * evaluate.
	 *
	 * @param c
	 *            Collection<T>
	 * @param e
	 *            T
	 * @return Collection<T>
	 */
	void evaluate(Collection<T> c, T e);
}
