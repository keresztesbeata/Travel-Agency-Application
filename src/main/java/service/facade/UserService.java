package service.facade;

import model.User;
import service.exceptions.InvalidInputException;
import service.managers.UserManager;
import service.roles.UserRole;

public class UserService implements UserRole {
    private UserManager userManager = new UserManager();

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
}
