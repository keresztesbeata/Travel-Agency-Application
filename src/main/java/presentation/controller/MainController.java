package presentation.controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public Button loginBtn;

    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    private void closeMainView() {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }

    public void goToLogin() {
        try {
            viewLoaderFactory.openLoginView();
            closeMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToRegister() {
        try {
            viewLoaderFactory.openRegisterView();
            closeMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
