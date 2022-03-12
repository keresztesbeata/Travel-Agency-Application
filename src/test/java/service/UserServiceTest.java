package service;

import model.User;
import model.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.exceptions.InvalidInputException;
import service.roles.RegularUserRole;
import service.roles.UserRole;

class UserServiceTest {

    @InjectMocks
    UserRole userService = new UserService();

    @Order(value = 1)
    @Test
    void register() {
        RegularUserRole regularUserRole = (RegularUserRole)userService;

        User validUser = new User("jack@sparrow", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> regularUserRole.register(validUser));

        User userWithInvalidUsername = new User(null, "blackpearl", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> regularUserRole.register(userWithInvalidUsername));

        User userWithInvalidPassword = new User("david@jones", "heart", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> regularUserRole.register(userWithInvalidPassword));

        Assertions.assertNull(regularUserRole.getCurrentUser());
    }

    @Order(value = 2)
    @Test
    void login() {
        User validUser = new User("jack@sparrow", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> userService.login(validUser));
        Assertions.assertEquals(userService.getCurrentUser().getUsername(), validUser.getUsername());

        User userWithIncorrectUsername = new User("sparrow@jack", "Black.Pearl1", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithIncorrectUsername));

        User userWithIncorrectPassword = new User("jack@sparrow", "Pearl.Black1", UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithIncorrectPassword));

        User userWithMissingCredentials = new User("jack@sparrow", null, UserType.REGULAR_USER);
        Assertions.assertThrows(InvalidInputException.class, () -> userService.login(userWithMissingCredentials));
    }

    @Order(value = 3)
    @Test
    void logout() {
        User validUser = new User("black@beard", "Black.Bird1", UserType.REGULAR_USER);
        Assertions.assertDoesNotThrow(() -> ((RegularUserRole) userService).register(validUser));
        Assertions.assertDoesNotThrow(() -> userService.login(validUser));
        Assertions.assertNotNull(userService.getCurrentUser());
        userService.logout();
        Assertions.assertNull(userService.getCurrentUser());
    }
}