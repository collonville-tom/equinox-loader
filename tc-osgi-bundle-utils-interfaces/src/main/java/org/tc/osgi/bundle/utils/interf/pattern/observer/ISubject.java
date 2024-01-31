package org.tc.osgi.bundle.utils.interf.pattern.observer;

import java.util.List;

/**
 * ISubject.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_070
 */
public interface ISubject<T extends IObserver, N extends IObserverEvent> {

	/**
	 * addObserver.
	 *
	 * @param _observer
	 *            IObserver
	 * @return boolean
	 */
	public boolean addObserver(T _observer);

	/**
	 * @return boolean
	 */
	public boolean notifyObservers();

	/**
	 * notifyObservers.
	 *
	 * @param _event
	 *            IObserverEvent
	 * @return boolean
	 */
	public boolean notifyObservers(N _event);

	/**
	 * notifyObservers.
	 *
	 * @param _events
	 *            List<IObserverEvent>
	 */
	void notifyObservers(List<N> _events);

	/**
	 * removeObserver.
	 *
	 * @param _observer
	 *            IObserver
	 * @return boolean
	 */
	public boolean removeObserver(T _observer);
}
