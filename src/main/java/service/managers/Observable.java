package service.managers;

import java.beans.PropertyChangeListener;

/**
 * Represents an observable entity, which can register or remove listeners.
 * <p>Used in implementing the observable pattern, in which the registered listeners can notified whenever
 * a property of teh observable mode changes.</p>
 */
public interface Observable {
    /**
     * Registers a new listener to the observable class.
     * @param listener the listener which should listen to changes in the properties of the class
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Removes a listener from the observable class.
     * @param listener the listener which should be removed
     */
    void removePropertyChangeListener(PropertyChangeListener listener);
}