/**
 */
package org.tc.osgi.bundle.utils.interf.pattern.command;

import org.tc.osgi.bundle.utils.interf.pattern.command.exception.CommandExecutionException;

/**
 * ICommand.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_080
 */
public interface ICommand {

	/**
	 * exec.
	 *
	 * @throws CommandExecutionException
	 */
	public abstract void exec() throws CommandExecutionException;

}
