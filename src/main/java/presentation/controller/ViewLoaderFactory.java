package presentation.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserType;
import presentation.views.UIComponentsFactory;

import java.io.IOException;
import java.util.HashMap;

public class ViewLoaderFactory {
    private static final String LOGIN_VIEW_URL = "/fxml/LoginView.fxml";
    private static final String REGISTER_VIEW_URL = "/fxml/RegisterView.fxml";
    private static final String MAIN_VIEW_URL = "/fxml/MainView.fxml";
    private static final String REGULAR_USER_VIEW_URL = "/fxml/RegularUserView.fxml";
    private static final String TRAVEL_AGENCY_VIEW_URL = "/fxml/TravelAgencyView.fxml";

    private static HashMap<String,Object> viewToController = new HashMap<>();

    public void openLoginView(UserType userType) throws IOException{
        openView(LOGIN_VIEW_URL, "Login");
        LoginController controller = (LoginController) viewToController.get(LOGIN_VIEW_URL);
        controller.init(userType);
    }

    public void openRegisterView() throws IOException {
        openView(REGISTER_VIEW_URL, "Register").setOnCloseRequest(e -> {
            try {
                openLoginView(UserType.REGULAR_USER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });;
    }

    public void openMainView() throws IOException {
        openView(MAIN_VIEW_URL,  "Main").setOnCloseRequest(e -> Platform.exit());
    }

    public void openRegularUserView() throws IOException {
        openView(REGULAR_USER_VIEW_URL,  "Home (Regular User)").setOnCloseRequest(e -> Platform.exit());
    }

    public void openTravelAgencyView() throws IOException {
        openView(TRAVEL_AGENCY_VIEW_URL,  "Home (Travel agency)").setOnCloseRequest(e -> Platform.exit());
    }

    private Stage openView(String url, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = loader.load(UIComponentsFactory.class.getResourceAsStream(url));
        viewToController.put(url, loader.getController());
        Scene scene = new Scene(mainPane);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return stage;
    }

    public Object getController(String url) {
        return viewToController.get(url);
    }
}
