package presentation.controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.UserType;

import java.io.IOException;

public class MainController {
    public Button travelAgencyButton;

    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    public void continueAsTravelAgency() {
        try {
            viewLoaderFactory.openLoginView(UserType.TRAVEL_AGENCY);
            closeMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void continueAsRegularUser() {
        try {
            viewLoaderFactory.openLoginView(UserType.REGULAR_USER);
            closeMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeMainView() {
        Stage stage = (Stage) travelAgencyButton.getScene().getWindow();
        stage.close();
    }

}
