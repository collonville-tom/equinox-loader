package org.tc.osgi.bundle.utils.module.service.impl;

import org.tc.osgi.bundle.utils.interf.module.service.ICommandRunnerUtilsService;
import org.tc.osgi.bundle.utils.interf.pattern.command.ICommand;
import org.tc.osgi.bundle.utils.interf.pattern.command.ICommandRepository;
import org.tc.osgi.bundle.utils.pattern.command.DefaultRepository;
import org.tc.osgi.bundle.utils.pattern.command.DefaultRunner;

public class CommandRunnerUtilsServiceImpl implements ICommandRunnerUtilsService {

	private static CommandRunnerUtilsServiceImpl instance = null;

	public static CommandRunnerUtilsServiceImpl getInstance() {
		if (CommandRunnerUtilsServiceImpl.instance == null) {
			CommandRunnerUtilsServiceImpl.instance = new CommandRunnerUtilsServiceImpl();
		}
		return CommandRunnerUtilsServiceImpl.instance;
	}

	private CommandRunnerUtilsServiceImpl() {

	}

	public ICommandRepository getRepository() {
		return DefaultRepository.getInstance();
	}

	public ICommand getRunner() {
		return new DefaultRunner();
	}

}
