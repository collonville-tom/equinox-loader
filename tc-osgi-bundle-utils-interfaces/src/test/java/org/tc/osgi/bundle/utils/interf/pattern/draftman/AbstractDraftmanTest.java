package org.tc.osgi.bundle.utils.interf.pattern.draftman;

import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitable;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitor;

/**
 * ObserverSubjectTest.java.
 *
 *
 * @req STD_BUNDLE_UTILS_070
 *
 * @track SRS_BUNDLE_UTILS_050
 * @track SDD_BUNDLE_UTILS_070
 * @author thomas collonvillé
 * @version 0.0.2
 */
public class AbstractDraftmanTest {

	@Test
	public void test() {
		final AbstractDraftman draftman = new AbstractDraftman(null) {

			@Override
			public String toString() {
				return "Draftman test";
			}

			@Override
			public void visit(final IVisitable o) {
				o.toString();
			}
		};
		new IVisitable() {

			@Override
			public void accept(final IVisitor visitor) {
				visitor.visit(this);
			}
		}.accept(draftman);

	}

}
