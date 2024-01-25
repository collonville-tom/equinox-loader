package org.tc.osgi.bundle.utils.module.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;
import org.tc.osgi.bundle.utils.interf.module.service.ICollectionUtilsService;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * Collections.java.
 *
 *
 * @author collonville thomas
 * @version 0.0.1
 * @track SDD_BUNDLE_UTILS_010
 */
public final class CollectionUtilsServiceImpl implements ICollectionUtilsService {

	private static CollectionUtilsServiceImpl instance = null;

	public static CollectionUtilsServiceImpl getInstance() {
		if (CollectionUtilsServiceImpl.instance == null) {
			CollectionUtilsServiceImpl.instance = new CollectionUtilsServiceImpl();
		}
		return CollectionUtilsServiceImpl.instance;
	}

	/**
	 * array2List.
	 * @param bundles
	 * @return
	 */
	public <T> Collection<T> array2List(final T[] t) {
		final Collection<T> c = new ArrayList<T>();
		for (final T _t : t) {
			c.add(_t);
		}
		return c;
	}

	/**
	 * collect.
	 *
	 * @param c
	 *            Collection<T>
	 * @param t
	 *            Transformer<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> collect(final Collection<T> c, final ITransformer<T> t) {
		Collection<T> res = null;
		try {
			res = c.getClass().getDeclaredConstructor(null).newInstance();
			for (final T e : c) {
				t.evaluate(res, e);
			}
		} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			LoggerGestionnary.getInstance(CollectionUtilsServiceImpl.class).error(e.getMessage(), e);
		}
		return res;
	}

	/**
	 * detect.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return boolean
	 */
	public <T> boolean detect(final Collection<T> c, final IPredicate<T> p) {
		for (final T e : c) {
			if (p.evaluate(e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * extract.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return T
	 */
	public <T> T extract(final Collection<T> c, final IPredicate<T> p) {
		for (final T e : c) {
			if (p.evaluate(e)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * reject.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> reject(final Collection<T> c, final IPredicate<T> p) {
		Collection<T> res = null;
		try {
			res = c.getClass().newInstance();
			for (final T e : c) {
				if (!p.evaluate(e)) {
					res.add(e);
				}
			}
		} catch (final InstantiationException e1) {
			LoggerGestionnary.getInstance(CollectionUtilsServiceImpl.class).error(e1.getMessage(), e1);
		} catch (final IllegalAccessException e1) {
			LoggerGestionnary.getInstance(CollectionUtilsServiceImpl.class).error(e1.getMessage(), e1);
		}
		return res;
	}

	/**
	 * select.
	 *
	 * @param c
	 *            Collection<T>
	 * @param p
	 *            Predicate<T>
	 * @return Collection<T>
	 */
	public <T> Collection<T> select(final Collection<T> c, final IPredicate<T> p) {
		Collection<T> res = null;
		try {
			res = c.getClass().newInstance();
			for (final T e : c) {
				if (p.evaluate(e)) {
					res.add(e);
				}
			}
		} catch (final InstantiationException e1) {
			LoggerGestionnary.getInstance(CollectionUtilsServiceImpl.class).error(e1.getMessage(), e1);
		} catch (final IllegalAccessException e1) {
			LoggerGestionnary.getInstance(CollectionUtilsServiceImpl.class).error(e1.getMessage(), e1);
		}
		return res;
	}
}
