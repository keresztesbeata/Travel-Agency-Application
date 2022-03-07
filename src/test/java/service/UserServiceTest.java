package service;

import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.exceptions.InvalidInputException;

class UserServiceTest {

    @InjectMocks
    UserService userService = new UserService();

    @Order(value = 1)
    @Test
    void register() {
        User validUser = new User("black@beard", "Black.Bird1");
        Assertions.assertDoesNotThrow(() -> userService.register(validUser));

        User userWithInvalidUsername = new User(null, "blackpearl");
        Assertions.assertThrows(InvalidInputException.class, () -> userService.register(userWithInvalidUsername));

        User userWithInvalidPassword = new User("david@jones", "heart");
        Assertions.assertThrows(InvalidInputException.class, () -> userService.register(userWithInvalidPassword));

        Assertions.assertNull(userService.getCurrentUser());
    }

    @Order(value = 2)
    @Test
    void login() {
        User validUser = new User("jack@sparrow", "Black.Pearl1");
        validUser.setId(1L);
        Assertions.assertDoesNotThrow(() -> userService.login(validUser));
        Assertions.assertEquals(userService.getCurrentUser().getId(), validUser.getId());

        User userWithIncorrectUsername = new User("sparrow@jack", "Black.Pearl1");
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithIncorrectUsername));

        User userWithIncorrectPassword = new User("jack@sparrow", "Pearl.Black1");
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithIncorrectPassword));

        User userWithMissingCredentials = new User("jack@sparrow", null);
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithMissingCredentials));
    }

    @Order(value = 3)
    @Test
    void logout() {
        Assertions.assertNotNull(userService.getCurrentUser());
        userService.logout();
        Assertions.assertNull(userService.getCurrentUser());
    }
}