package service.facade;

import model.User;
import service.exceptions.InvalidInputException;
import service.managers.UserManager;
import service.facade.roles.UserRole;
import service.managers.VacationDestinationManager;

import java.beans.PropertyChangeListener;

public class UserService implements UserRole {
    private UserManager userManager = UserManager.getInstance();

    @Override
    public User login(User user) throws InvalidInputException {
        return userManager.login(user);
    }

    @Override
    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    @Override
    public void logout() {
        userManager.logout();
    }

    @Override
    public void registerListener(PropertyChangeListener listener) {

    }

    @Override
    public void removeListener(PropertyChangeListener listener) {

    }
}
