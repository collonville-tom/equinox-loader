package org.tc.osgi.bundle.utils.interf.collection.element;

import java.io.Serializable;

/**
 * Pair.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_015
 *
 */
public class Pair<T, N> implements Serializable {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 6327674735067582402L;

	/**
	 * T first.
	 */
	private T first = null;

	/**
	 * N second.
	 */
	private N second = null;

	/**
	 * Pair constructor.
	 */
	public Pair() {
		super();
	}

	/**
	 * Pair constructor.
	 *
	 * @param _first
	 *            T
	 * @param _second
	 *            N
	 */
	public Pair(final T _first, final N _second) {
		super();
		this.first = _first;
		this.second = _second;
	}

	/**
	 * @param obj
	 *            Object
	 * @return boolean
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Pair other = (Pair) obj;
		if (this.first == null) {
			if (other.first != null) {
				return false;
			}
		} else
			if (!this.first.equals(other.first)) {
				return false;
			}
		if (this.second == null) {
			if (other.second != null) {
				return false;
			}
		} else
			if (!this.second.equals(other.second)) {
				return false;
			}
		return true;
	}

	/**
	 * getFirst.
	 *
	 * @return T
	 */
	public T getFirst() {
		return this.first;
	}

	/**
	 * getSecond.
	 *
	 * @return N
	 */
	public N getSecond() {
		return this.second;
	}

	/**
	 * @return int
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.first == null) ? 0 : this.first.hashCode());
		result = (prime * result) + ((this.second == null) ? 0 : this.second.hashCode());
		return result;
	}

	/**
	 * setFirst.
	 *
	 * @param t
	 *            T
	 */
	public void setFirst(final T t) {
		this.first = t;
	}

	/**
	 * setSecond.
	 *
	 * @param n
	 *            N
	 */
	public void setSecond(final N n) {
		this.second = n;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("Pair(");
		buff.append(this.first.toString()).append(",");
		buff.append(this.second.toString()).append(")");
		return buff.toString();

	}
}
