package org.tc.osgi.bundle.utils.interf.module.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

public class TcOsgiProxy<T> implements InvocationHandler {

	private T service;
	private T proxy;

	public void setProxy(T proxy) {
		this.proxy = proxy;
	}

	private TcOsgiServiceTracker<T> tracker;

	public TcOsgiProxy(BundleContext context, Class<T> t) throws TcOsgiException {
		try {
			this.tracker = new TcOsgiServiceTracker<T>(context, t);

			tracker.open();
			this.service = (T) tracker.getService();
			if (this.service != null) {
				this.proxy = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), this);
			}
		} catch (InvalidSyntaxException | BundleException e) {
			throw new TcOsgiException("TcOsgiServiceTracker not found", e);
		}
	}

	public T getInstance() {
		return this.proxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(service, args);
	}

	public void close() {
		this.tracker.close();
	}

}
