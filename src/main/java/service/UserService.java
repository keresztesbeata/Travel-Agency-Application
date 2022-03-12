package service;

import model.User;
import repository.UserRepository;
import service.exceptions.InvalidInputException;
import service.roles.UserRole;

public class UserService implements UserRole {
    private static UserRepository userRepository = UserRepository.getInstance();
    private User currentUser;

    public void login(User user) throws InvalidInputException {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new InvalidInputException("The username and password should not be empty!");
        }
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            throw new InvalidInputException("Invalid username!");
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new InvalidInputException("Invalid password!");
        }
        currentUser = existingUser;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
