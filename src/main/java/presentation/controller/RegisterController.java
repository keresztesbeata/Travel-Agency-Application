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
import service.facade.RegularUserServiceFacade;
import service.roles.RegularUserRole;

import java.io.IOException;

public class RegisterController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button registerButton;
    private RegularUserRole userRole = new RegularUserServiceFacade();
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    public void onRegister() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            userRole.register(new User(username, password, UserType.REGULAR_USER));
            viewLoaderFactory.openLoginView(UserType.REGULAR_USER);
            closeRegisterView();
        } catch (InvalidInputException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error while registering", e.getMessage())
                    .showAndWait();
            clearInputFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeRegisterView() {
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
