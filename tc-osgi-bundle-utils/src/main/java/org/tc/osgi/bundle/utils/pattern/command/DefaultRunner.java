package org.tc.osgi.bundle.utils.pattern.command;

import java.util.Iterator;

import org.tc.osgi.bundle.utils.interf.pattern.command.ICommand;
import org.tc.osgi.bundle.utils.interf.pattern.command.exception.CommandExecutionException;
import org.tc.osgi.bundle.utils.module.service.impl.CommandRunnerUtilsServiceImpl;

/**
 * ExecuteInstructions.java.
 *
 * @author thomas collonvillé
 * @version 0.0.1
 * @track SDD_BUNDLE_UTILS_080
 */
public final class DefaultRunner implements ICommand {

	/**
	 *
	 * @see org.tc.osgi.bundle.utils.pattern.command.ICommand#exec()
	 */
	@Override
	public void exec() throws CommandExecutionException {
		final Iterator<ICommand> it = CommandRunnerUtilsServiceImpl.getInstance().getRepository().getCommandsIterator();
		while (it.hasNext()) {
			((ICommand) it.next()).exec();
		}
	}
}
