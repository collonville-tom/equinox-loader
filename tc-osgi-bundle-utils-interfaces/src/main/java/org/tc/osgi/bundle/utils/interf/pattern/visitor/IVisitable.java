package org.tc.osgi.bundle.utils.interf.pattern.visitor;

/**
 * IVisitable.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_050
 */
public interface IVisitable<T extends IVisitor> {

	/**
	 * accept.
	 *
	 * @param visitor
	 *            AbstractVisitor
	 */
	public void accept(T visitor);

}
