package service;

import model.User;
import repository.UserRepository;
import service.exceptions.InvalidInputException;
import service.validators.UserValidator;

public class UserService {
    private static UserRepository userRepository = UserRepository.getInstance();
    private User currentUser;

    public void register(User user) throws InvalidInputException {
        UserValidator userValidator = new UserValidator(user);
        userValidator.validate();

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new InvalidInputException("The username: " + user.getUsername() + " is already taken! Please select another one!");
        }
        userRepository.save(user);
    }

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
