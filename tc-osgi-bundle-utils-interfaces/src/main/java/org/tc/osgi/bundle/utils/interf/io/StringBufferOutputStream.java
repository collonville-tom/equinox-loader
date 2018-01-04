/**
 */
package org.tc.osgi.bundle.utils.interf.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * StringOutputStream.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_150
 */
public class StringBufferOutputStream extends OutputStream {

	/**
	 * StringBuilder value.
	 */
	private StringBuilder value = new StringBuilder();

	/**
	 * StringOutputStream constructor.
	 * @param value
	 */
	public StringBufferOutputStream() {
	}

	/**
	 * Getter value.
	 * @return StringBuilder
	 */
	public StringBuilder getValue() {
		return value;
	}

	/**
	 * Setter value.
	 * @param value StringBuffer
	 */
	public void setValue(final StringBuilder value) {
		this.value = value;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getValue().toString();
	}

	/**
	 * @param arg0 int
	 * @throws IOException
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(final int arg0) throws IOException {
		value.append((char) arg0);

	}

}
