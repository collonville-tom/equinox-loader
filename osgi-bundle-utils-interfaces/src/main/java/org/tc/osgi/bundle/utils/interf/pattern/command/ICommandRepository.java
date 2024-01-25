package org.tc.osgi.bundle.utils.interf.pattern.command;

import java.util.Iterator;

public interface ICommandRepository {

	public void addInstruction(ICommand cmd);

	public Iterator<ICommand> getCommandsIterator();

}
