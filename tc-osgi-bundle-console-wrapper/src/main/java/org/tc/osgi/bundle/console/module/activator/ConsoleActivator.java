package org.tc.osgi.bundle.console.module.activator;

import org.apache.log4j.xml.DOMConfigurator;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.console.conf.ConsolePropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.interf.module.utils.TcOsgiProxy;

/**
 * Activator.java.
 *
 * @author Collonville Thomas
 * @version 0.1.0
 **/
public class ConsoleActivator extends AbstractTcOsgiActivator {

	/**
	 * AptServiceTracker aptServiceTracker.
	 */

	private TcOsgiProxy<IPropertyUtilsService> iPropertyUtilsService;
	private TcOsgiProxy<ILoggerUtilsService> iLoggerUtilsService;

	/**
	 * String orgSpringframeworkOsgiExtenderDependencyBundleName.
	 */
	private String consoleDependencyBundleName;
	private String runtimeDependencyBundleName;
	private String shellDependencyBundleName;

	private String consoleDependencyBundleVersion;
	private String runtimeDependencyBundleVersion;
	private String shellDependencyBundleVersion;

	public String getConsoleDependencyBundleVersion() throws FieldTrackingAssignementException {
		if (consoleDependencyBundleVersion == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "consoleDependencyBundleVersion");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + consoleDependencyBundleVersion);
		return consoleDependencyBundleVersion;
	}

	public String getRuntimeDependencyBundleVersion() throws FieldTrackingAssignementException {
		if (runtimeDependencyBundleVersion == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "runtimeDependencyBundleVersion");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + runtimeDependencyBundleVersion);
		return runtimeDependencyBundleVersion;
	}

	public String getShellDependencyBundleVersion() throws FieldTrackingAssignementException {
		if (shellDependencyBundleVersion == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "shellDependencyBundleVersion");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + shellDependencyBundleVersion);
		return shellDependencyBundleVersion;
	}

	public String getConsoleDependencyBundleName() throws FieldTrackingAssignementException {
		if (consoleDependencyBundleName == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "consoleDependencyBundleName");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + consoleDependencyBundleName);
		return consoleDependencyBundleName;
	}

	public String getRuntimeDependencyBundleName() throws FieldTrackingAssignementException {
		if (runtimeDependencyBundleName == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "runtimeDependencyBundleName");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + runtimeDependencyBundleName);
		return runtimeDependencyBundleName;
	}

	public String getShellDependencyBundleName() throws FieldTrackingAssignementException {
		if (shellDependencyBundleName == null) {
			this.iPropertyUtilsService.getInstance().getYamlPropertyFile(ConsolePropertyFile.getInstance().getYamlFile()).fieldTraking(this, "shellDependencyBundleName");
		}
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Lancement auto du bundle :" + shellDependencyBundleName);
		return shellDependencyBundleName;
	}

	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		throw new TcOsgiException("checkInitBundleUtilsService not implemented");

	}

	@Override
	protected void initProxys(BundleContext context) throws TcOsgiException {

		this.iPropertyUtilsService = new TcOsgiProxy<IPropertyUtilsService>(context, IPropertyUtilsService.class);
		this.iLoggerUtilsService = new TcOsgiProxy<ILoggerUtilsService>(context, ILoggerUtilsService.class);

	}

	@Override
	protected void initServices(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {

	}

	@Override
	protected void detachServices(BundleContext context) throws TcOsgiException {
		this.iLoggerUtilsService.close();
		this.iLoggerUtilsService.close();

	}

	@Override
	protected void beforeStart(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Stop of utils service tracking");

	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		// http://logging.apache.org/log4j/1.2/manual.html
		// BasicConfigurator.configure();
		DOMConfigurator.configureAndWatch(this.iLoggerUtilsService.getInstance().getLoggerGestionnary().getLog4jPathfile());
		this.iLoggerUtilsService.getInstance().getLogger(ConsoleActivator.class).debug("Start of utils service tracking");
		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context, getRuntimeDependencyBundleName(), this.getRuntimeDependencyBundleVersion());
		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context, getShellDependencyBundleName(), this.getShellDependencyBundleVersion());
		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context, getConsoleDependencyBundleName(), this.getConsoleDependencyBundleVersion());

	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}
}
