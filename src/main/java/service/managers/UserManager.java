package service.managers;

import service.validators.InputValidator;
import service.validators.UserValidator;
import model.User;
import repository.UserRepository;
import service.exceptions.InvalidInputException;

public class UserManager {
    private static UserRepository userRepository = UserRepository.getInstance();
    private InputValidator<User> userValidator = new UserValidator();
    private static Long userId;
    private static UserManager instance;

    private UserManager() {}

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User login(User user) throws InvalidInputException {
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
        userId = existingUser.getId();
        return existingUser;
    }

    public void register(User user) throws InvalidInputException {
        userValidator.validate(user);

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new InvalidInputException("The username: " + user.getUsername() + " is already taken!\nPlease select another one!");
        }
        userRepository.save(user);
    }

    public void logout() {
        userId = null;
    }

    public User getCurrentUser() {
        if(userId != null) {
            return userRepository.findById(userId);
        }
        return null;
    }

}
