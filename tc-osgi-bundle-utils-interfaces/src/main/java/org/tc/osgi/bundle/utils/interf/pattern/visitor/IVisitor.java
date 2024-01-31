package org.tc.osgi.bundle.utils.interf.pattern.visitor;

/**
 * Visitor.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_050
 */
public interface IVisitor<T extends IVisitable> {

	/**
	 * visit.
	 *
	 * @param o
	 *            IVisitable
	 */
	public void visit(T o);
}
