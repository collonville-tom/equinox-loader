package org.tc.osgi.bundle.utils.interf.module.service;

import org.tc.osgi.bundle.utils.interf.pattern.command.ICommand;
import org.tc.osgi.bundle.utils.interf.pattern.command.ICommandRepository;

public interface ICommandRunnerUtilsService {

	public ICommandRepository getRepository();

	public ICommand getRunner();

}
