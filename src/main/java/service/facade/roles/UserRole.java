package service.facade.roles;

import model.User;
import service.exceptions.InvalidInputException;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface UserRole {
    User login(User user) throws InvalidInputException;
    User getCurrentUser();
    void logout();
    void registerListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
