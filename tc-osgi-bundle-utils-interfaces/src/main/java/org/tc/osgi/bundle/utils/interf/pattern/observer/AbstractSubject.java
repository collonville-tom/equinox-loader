package org.tc.osgi.bundle.utils.interf.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractSubject.java.
 * 
 * Implementation par defaut du sujet du pattern objservateur
 * 
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_070
 */
public abstract class AbstractSubject<T extends IObserver, N extends IObserverEvent> implements ISubject<T, N> {

	/**
	 * List<IObserver> observerList.
	 */
	private List<T> observerList = null;

	/**
	 * AbstractSubject constructor.
	 */
	public AbstractSubject() {
		super();
	}

	/**
	 * @param _observer
	 *            IObserver
	 * @return boolean
	 * @see org.tc.osgi.bundle.utils.pattern.observer.ISubject#addObserver(org.tc.osgi.bundle.utils.pattern.observer.IObserver)
	 */
	@Override
	public boolean addObserver(final T _observer) {
		if (_observer != null) {
			return this.getObserverList().add(_observer);
		}
		return false;
	}

	/**
	 * getObserverList.
	 * 
	 * @return List<IObserver>
	 */
	public List<T> getObserverList() {
		if (this.observerList == null) {
			this.observerList = new ArrayList<T>();
		}
		return this.observerList;
	}

	@Override
	public boolean notifyObservers() {
		if (this.observerList != null) {
			for (final T o : new ArrayList<T>(this.getObserverList())) {
				o.update(this);
			}
			return true;
		}
		return false;
	}

	/**
	 * @param _event
	 *            IObserverEvent
	 * @return boolean
	 * @see org.tc.osgi.bundle.utils.pattern.observer.ISubject#notifyObservers(org.tc.osgi.bundle.utils.pattern.observer.IObserverEvent)
	 */
	@Override
	public boolean notifyObservers(final N _event) {
		if (this.observerList != null) {
			for (final T o : new ArrayList<T>(this.getObserverList())) {
				o.update(this, _event);
			}
			return true;
		}
		return false;
	}

	/**
	 * @param _events
	 *            List<IObserverEvent>
	 * @see org.tc.osgi.bundle.utils.pattern.observer.ISubject#notifyObservers(java.util.List)
	 */
	@Override
	public void notifyObservers(final List<N> _events) {
		if (_events != null) {
			for (final N event : _events) {
				this.notifyObservers(event);
			}
		}
	}

	/**
	 * @param _observer
	 *            IObserver
	 * @return boolean
	 * @see org.tc.osgi.bundle.utils.pattern.observer.ISubject#removeObserver(org.tc.osgi.bundle.utils.pattern.observer.IObserver)
	 */
	@Override
	public boolean removeObserver(final T _observer) {
		if (this.observerList == null) {
			return false;
		}
		return this.getObserverList().remove(_observer);
	}

	/**
	 * setObserverList.
	 *  
	 * @param _observerLis t
	 *            List<IObserver>
	 */
	public void setObserverList(final List<T> _observerList) {
		this.observerList = _observerList;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buff = new StringBuilder("ObserveurList:");
		for (final T observer : this.getObserverList()) {
			buff.append(observer.getClass().toString());
			if (!observer.equals(this.getObserverList().get(this.getObserverList().size() - 1))) {
				buff.append(",");
			}

		}
		return buff.toString();
	}

}
