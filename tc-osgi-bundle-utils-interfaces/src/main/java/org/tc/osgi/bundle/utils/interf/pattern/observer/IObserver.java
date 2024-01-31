package org.tc.osgi.bundle.utils.interf.pattern.observer;

/**
 * IObserver.java.
 *
 * @author collonville thomas
 * @version 0.0.2
 * @track SDD_BUNDLE_UTILS_070
 */
public interface IObserver<T extends ISubject, N extends IObserverEvent> {

	public void update(T _subject);

	/**
	 * update.
	 *
	 * @param _subject
	 *            ISubject
	 * @param _event
	 *            IObserverEvent
	 */
	public void update(T _subject, N _event);
}
