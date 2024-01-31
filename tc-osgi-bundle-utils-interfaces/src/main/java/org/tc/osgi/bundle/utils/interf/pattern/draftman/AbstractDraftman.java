package org.tc.osgi.bundle.utils.interf.pattern.draftman;

import java.awt.Graphics;

import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitor;

/**
 * AbstractDraftman.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_060
 */
public abstract class AbstractDraftman implements IVisitor {

	/**
	 * Graphics graphics.
	 */
	private Graphics graphics;

	/**
	 * AbstractDraftman constructor.
	 *
	 * @param g
	 *            Graphics
	 */
	public AbstractDraftman(final Graphics g) {
		graphics = g;
	}

	/**
	 * getGraphics.
	 *
	 * @return Graphics
	 */
	public Graphics getGraphics() {
		return graphics;
	}

	/**
	 * setGraphics.
	 *
	 * @param graphics
	 *            Graphics
	 */
	public void setGraphics(final Graphics graphics) {
		this.graphics = graphics;
	}

}
