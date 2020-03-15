package org.tc.osgi.bundle.utils.pattern.observer;

import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.pattern.observer.AbstractSubject;
import org.tc.osgi.bundle.utils.interf.pattern.observer.IObserver;
import org.tc.osgi.bundle.utils.interf.pattern.observer.IObserverEvent;
import org.tc.osgi.bundle.utils.interf.pattern.observer.ISubject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ObserverSubjectTest.java.
 *
 * @author thomas collonvillé
 * @version 0.0.2
 * @req STD_BUNDLE_UTILS_060
 * @track SRS_BUNDLE_UTILS_050
 * @track SDD_BUNDLE_UTILS_070
 */
public class ObserverSubjectTest {

    /**
     * testObserverSubject.
     */
    @Test
    public void testObserverSubject() {

        final AbstractSubject subject = new AbstractSubject() {
        };
        final IObserverEvent event = new IObserverEvent() {
        };

        final IObserver observer = new IObserver() {

            @Override
            public void update(final ISubject _subject) {
                assertEquals(subject, _subject);

            }

            @Override
            public void update(final ISubject _subject, final IObserverEvent _event) {
                assertEquals(subject, _subject);
                assertEquals(event, _event);

            }
        };

        subject.addObserver(observer);
        subject.notifyObservers(event);
        subject.notifyObservers();
        subject.toString();
        subject.removeObserver(observer);
    }

}
