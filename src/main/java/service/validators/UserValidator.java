package business.validators;

import model.User;
import business.exceptions.InvalidInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements InputValidator<User> {
    private static final int MAX_USERNAME_LENGTH = 100;
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-z])(?=.*[_.#@!&$]).{8,100}$";

    @Override
    public void validate(User user) throws InvalidInputException {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
    }

    private void validateUsername(String username) throws InvalidInputException {
        if (username == null) {
            throw new InvalidInputException("The username should not be empty!");
        }
        if (username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidInputException("The username: " + username + " has invalid length! It should be less than " + MAX_USERNAME_LENGTH + "!");
        }
    }

    private void validatePassword(String password) throws InvalidInputException {
        if (password == null) {
            throw new InvalidInputException("The password should not be empty!");
        }
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new InvalidInputException("The password: " + password + " has an invalid format! It should contain at least 1 digit and a special character from '.','_' or '#'!");
        }
    }
}
