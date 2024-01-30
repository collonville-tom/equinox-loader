package org.tc.osgi.equinox.loader.cmd.context;

import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.tc.osgi.bundle.utils.conf.XMLPropertyFile;
import org.tc.osgi.bundle.utils.context.BundleInstaller;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;
import org.tc.osgi.equinox.loader.conf.exception.EquinoxConfigException;

/**
 * FilterValidBundleCmd.java.
 *
 * @author Collonville Thomas
 * @version 0.0.3
 */
public class FilterValidBundleCmd extends AbstractBundleContextCmd {

	/**
	 * String UNKNOW_NAME_BUNDLE.
	 */
	public final static String UNKNOW_NAME_BUNDLE = "unknown";

	/**
	 * String utilsDependencyBundleName.
	 */
	// TODO Maybe remove
	private String utilsDependencyBundleName;

	/**
	 * FilterValidBundleCmd constructor.
	 *
	 * @param context BundleContext
	 */
	public FilterValidBundleCmd(final BundleContext context) {
		super(context);
	}

	/**
	 * @throws EquinoxCmdException
	 * @see org.tc.osgi.equinox.loader.cmd.AbstractEquinoxCmd#execute()
	 */
	@Override
	public void execute() throws EquinoxCmdException {
		try {
			Collection<Bundle> bundleList = CollectionUtilsServiceImpl.getInstance().array2List(context.getBundles());
			bundleList = preloadDefaultBundle(bundleList);

			for (final Bundle bundle : bundleList) {
				if (bundle.toString().startsWith(FilterValidBundleCmd.UNKNOW_NAME_BUNDLE)) {
					LoggerGestionnary.getInstance(FilterValidBundleCmd.class).debug("Uninstall invalid bundle:" + bundle.getLocation());
					bundle.uninstall();
				} else {
//                    if (!bundle.toString().startsWith("log4j")) {
					try {
						LoggerGestionnary.getInstance(FilterValidBundleCmd.class).debug("Stating bundle:" + bundle.getLocation());
						bundle.start();
						LoggerUtilsServiceImpl.getInstance().getLogger(FilterValidBundleCmd.class)
								.debug(bundle.getSymbolicName() + "[" + bundle.getBundleId() + "] with state " + this.getBundleState(bundle) + " installed");
					} catch (final Throwable e) {
						LoggerGestionnary.getInstance(FilterValidBundleCmd.class).error(e);
						LoggerGestionnary.getInstance(FilterValidBundleCmd.class).info("Skipping");
						// bundle.uninstall();
					}
//                    }
				}
			}
		} catch (final Throwable e) {
			LoggerGestionnary.getInstance(FilterValidBundleCmd.class).error(e);
			// throw new EquinoxCmdException("Filter Cmd error", e);
		}
	}

	public static String getBundleState(Bundle bundle) {
		switch (bundle.getState()) {
		case 0x1:
			return "UNINSTALLED";
		case 0x2:
			return "INSTALLED";
		case 0x4:
			return "RESOLVED";
		case 0x8:
			return "STARTING";
		case 0x10:
			return "STOPPING";
		case 0x20:
			return "ACTIVE";
		default:
			throw new IllegalArgumentException("Unexpected value: " + bundle.getState());
		}
	}

	/**
	 * getUtilsDependencyBundleName.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 * @throws EquinoxCmdException
	 * @throws EquinoxConfigException
	 */
	public String getUtilsDependencyBundleName() throws FieldTrackingAssignementException, EquinoxConfigException, EquinoxCmdException {
		if (utilsDependencyBundleName == null) {
			XMLPropertyFile.getInstance(EquinoxPropertyFile.getInstance().getXMLFile()).fieldTraking(this, "utilsDependencyBundleName");
		}
		return utilsDependencyBundleName;
	}

	/**
	 * preloadDefaultBundle.
	 * 
	 * @param files Collection<Bundle>
	 * @return Collection<Bundle>
	 */
	private Collection<Bundle> preloadDefaultBundle(final Collection<Bundle> files) {
		final Collection<Bundle> result = CollectionUtilsServiceImpl.getInstance().reject(files, new IPredicate<Bundle>() {

			@Override
			public boolean evaluate(final Bundle e) {
				try {
					if ((e.getSymbolicName() != null) && e.getSymbolicName().matches(FilterValidBundleCmd.this.getUtilsDependencyBundleName())) {
						LoggerGestionnary.getInstance(this.getClass()).debug("Refresh auto du bundle :" + e.getSymbolicName());
						e.update();
						LoggerUtilsServiceImpl.getInstance().getLogger(BundleInstaller.class)
								.debug(e.getSymbolicName() + "[" + e.getBundleId() + "] with state " + getBundleState(e) + " installed");
						return true;
					}
				} catch (final FieldTrackingAssignementException e1) {
					LoggerGestionnary.getInstance(this.getClass()).error("Erreur installation Bundle par defaut Utils", e1);
				} catch (final EquinoxConfigException e1) {
					LoggerGestionnary.getInstance(this.getClass()).error("Erreur installation Bundle par defaut Utils", e1);
				} catch (final EquinoxCmdException e1) {
					LoggerGestionnary.getInstance(this.getClass()).error("Erreur installation Bundle par defaut Utils", e1);
				} catch (final BundleException e1) {
					LoggerGestionnary.getInstance(this.getClass()).error("Erreur installation Bundle par defaut Utils", e1);
				}
				return false;
			}
		});

		return result;
	}
}
