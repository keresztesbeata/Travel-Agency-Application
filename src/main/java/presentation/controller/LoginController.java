package presentation.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserType;
import presentation.views.UIComponentsFactory;
import service.exceptions.InvalidInputException;
import service.facade.UserServiceFactory;
import service.roles.UserRole;

import java.io.IOException;

import static model.UserType.REGULAR_USER;
import static model.UserType.TRAVEL_AGENCY;

public class LoginController {

    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button registerButton;
    private UserRole userRole;
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private UserType userType;

    public void init(UserType userType) {
        this.userType = userType;
        UserServiceFactory userServiceFactory = new UserServiceFactory();
        userRole = userServiceFactory.getUserRole(userType);
        registerButton.setDisable(isAdmin(userType));
    }

    public void onLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            userRole.login(new User(username, password));
            if (userType.equals(REGULAR_USER)) {
                viewLoaderFactory.openRegularUserView();
            } else {
                viewLoaderFactory.openTravelAgencyView();
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

    private boolean isAdmin(UserType userType) {
        return userType.equals(TRAVEL_AGENCY);
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