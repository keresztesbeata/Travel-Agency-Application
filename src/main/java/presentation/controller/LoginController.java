package presentation.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import presentation.views.UIComponentsFactory;
import service.exceptions.InvalidInputException;
import service.facade.UserService;
import service.facade.roles.UserRole;

import java.io.IOException;

import static model.UserType.REGULAR_USER;

public class LoginController {

    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button registerButton;
    private UserRole userRole = new UserService();
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    public void onLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            User existingUser = userRole.login(new User(username, password));
            if (existingUser.getUserType().equals(REGULAR_USER)) {
                viewLoaderFactory.openRegularUserView();
            } else {
                viewLoaderFactory.openTravelAgencyPackagesView();
            }
            closeLoginView();
        } catch (InvalidInputException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error while logging in", e.getMessage())
                    .showAndWait();
            clearInputFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToRegister() {
        try {
            viewLoaderFactory.openRegisterView();
            closeLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLoginView() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

    private void clearInputFields() {
        usernameInput.clear();
        usernameInput.setPromptText("Enter username...");
        passwordInput.clear();
        passwordInput.setPromptText("Enter password...");
    }
}