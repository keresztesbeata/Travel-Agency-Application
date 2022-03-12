package service;

import model.User;
import model.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.exceptions.InvalidInputException;
import service.managers.UserManager;
import service.roles.RegularUserRole;

class UserManagerTest {

    UserManager userManager = new UserManager();

    @Order(value = 1)
    @Test
    void register() {
        User validUser = new User("jack@sparrow", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> userManager.register(validUser));

        User userWithInvalidUsername = new User(null, "blackpearl", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userManager.register(userWithInvalidUsername));

        User userWithInvalidPassword = new User("david@jones", "heart", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userManager.register(userWithInvalidPassword));

        Assertions.assertNull(userManager.getCurrentUser());
    }

    @Order(value = 2)
    @Test
    void login() {
        User validUser = new User("jack@sparrow", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> userManager.login(validUser));
        Assertions.assertEquals(userManager.getCurrentUser().getUsername(), validUser.getUsername());

        User userWithIncorrectUsername = new User("sparrow@jack", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userManager.login(userWithIncorrectUsername));

        User userWithIncorrectPassword = new User("jack@sparrow", "Pearl.Black1", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userManager.login(userWithIncorrectPassword));

        User userWithMissingCredentials = new User("jack@sparrow", null, UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userManager.login(userWithMissingCredentials));
    }

    @Order(value = 3)
    @Test
    void logout() {
        User validUser = new User("black@beard", "Black.Bird1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> userManager.register(validUser));
        Assertions.assertDoesNotThrow(() -> userManager.login(validUser));
        Assertions.assertNotNull(userManager.getCurrentUser());
        userManager.logout();
        Assertions.assertNull(userManager.getCurrentUser());
    }
}