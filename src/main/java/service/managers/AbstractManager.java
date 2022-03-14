package service.managers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractManager implements Observable{
    /** Represents a support for easily adding or removing listeners as well as notifying them of any changes in the properties of the entity. */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);

    /** Represent the types of changes in the property. */
    public enum PROPERTY_CHANGE{
        ADDED_ENTRY,
        DELETED_ENTRY,
        UPDATED_ENTRY
    }

    /**
     * Attaches a new listener to the observed object.
     * @param listener the listener which should listen to changes in the properties of the class
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener from the observed object.
     * @param listener the listener which should be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
