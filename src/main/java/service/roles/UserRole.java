package service.roles;

import model.User;
import service.exceptions.InvalidInputException;

public interface UserRole {
    User login(User user) throws InvalidInputException;
    User getCurrentUser();
    void logout();
}
