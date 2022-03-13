package service.roles;

import model.User;
import service.exceptions.InvalidInputException;

import java.util.List;

public interface UserRole {
    User login(User user) throws InvalidInputException;
    User getCurrentUser();
    void logout();
}
