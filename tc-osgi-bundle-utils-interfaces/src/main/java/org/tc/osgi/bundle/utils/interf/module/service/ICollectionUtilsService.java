package org.tc.osgi.bundle.utils.interf.module.service;

import java.util.Collection;

import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;

/**
 * UtilsService.java.
 *
 * @author Collonville Thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_100
 */
public interface ICollectionUtilsService {
	/**
	* array2List.
	* @param bundles
	* @return
	*/
	public <T> Collection<T> array2List(final T[] t);

	/**
	 * collect.
	 *
	 * @param c
	 *            Collection<T>
	 * @param t
	 *            Transformer<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> collect(final Collection<T> c, final ITransformer<T> t);

	/**
	 * detect.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return boolean
	 */
	public <T> boolean detect(final Collection<T> c, final IPredicate<T> p);

	/**
	 * extract.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return T
	 */
	public <T> T extract(final Collection<T> c, final IPredicate<T> p);

	/**
	 * reject.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> reject(final Collection<T> c, final IPredicate<T> p);

	/**
	 * select.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> select(final Collection<T> c, final IPredicate<T> p);
}
