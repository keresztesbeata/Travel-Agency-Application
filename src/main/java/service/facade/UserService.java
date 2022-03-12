package service.facade;

import model.User;
import service.exceptions.InvalidInputException;
import service.managers.UserManager;
import service.roles.UserRole;

public abstract class UserService implements UserRole {
    private UserManager userManager = new UserManager();

    @Override
    public void login(User user) throws InvalidInputException {
        userManager.login(user);
    }

    @Override
    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    @Override
    public void logout() {
        userManager.logout();
    }
}
