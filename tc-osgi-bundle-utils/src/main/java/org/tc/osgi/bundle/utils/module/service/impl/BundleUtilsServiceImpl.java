package org.tc.osgi.bundle.utils.module.service.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.tc.osgi.bundle.utils.context.BundleKiller;
import org.tc.osgi.bundle.utils.context.BundleStarter;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.module.utils.TcOsgiServiceFactory;
import org.tc.osgi.bundle.utils.rmi.client.EquinoxLoaderRMIClient;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class BundleUtilsServiceImpl implements IBundleUtilsService {

	@Override
	public BundleContext getBundleContext() throws FieldTrackingAssignementException, MalformedURLException, RemoteException, NotBoundException,
		NumberFormatException, UnknownHostException {
		return EquinoxLoaderRMIClient.getInstance().getIEquinoxLoaderBundleContext().getBundleContext();
	}

	/**
	 * @return
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getBundleKiller()
	 */
	@Override
	public IBundleCommand getBundleKiller() {
		return new BundleKiller();
	}

	/**
	 * @return BundleStarter
	 * @see org.tc.osgi.bundle.utils.module.service.IUtilsService#getBundleStarter()
	 */
	@Override
	public IBundleCommand getBundleStarter() {
		return new BundleStarter();
	}

	/**
	 * @param loader ClassLoader
	 */
	public void getClassloaderContent(final ClassLoader loader) {

		Class clKlass = loader.getClass();
		final StringBuilder b = new StringBuilder("Classloader: ");
		b.append(clKlass.getCanonicalName());
		while (clKlass != java.lang.ClassLoader.class) {
			clKlass = clKlass.getSuperclass();
		}
		try {
			final java.lang.reflect.Field fldClasses = clKlass.getDeclaredField("classes");
			fldClasses.setAccessible(true);
			final Vector classes = (Vector) fldClasses.get(loader);
			b.append(", Loaded:[");
			for (final Iterator iter = classes.iterator(); iter.hasNext();) {
				b.append(iter.next()).append(",");
			}
			LoggerGestionnary.getInstance(BundleUtilsServiceImpl.class).debug(b.toString());
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final NoSuchFieldException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Map<Integer, ServiceRegistration> registry = new HashMap<Integer, ServiceRegistration>();

	@Override
	public <T> void registerService(Class<T> _class, T instance, final BundleContext context, BundleActivator activator) {
		registry.put(activator.hashCode() + _class.hashCode(), context.registerService(_class.getName(), new TcOsgiServiceFactory<T>(instance), null));
	}

	@Override
	public void unregister(Class _class, BundleActivator activator) {
		registry.get(activator.hashCode() + _class.hashCode()).unregister();
		registry.remove(activator.hashCode() + _class.hashCode());

	}
}
