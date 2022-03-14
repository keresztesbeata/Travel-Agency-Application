package presentation.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.views.UIComponentsFactory;
import service.dto.VacationPackageDTO;

import java.io.IOException;
import java.util.HashMap;

public class ViewLoaderFactory {
    private static final String LOGIN_VIEW_URL = "/fxml/LoginView.fxml";
    private static final String REGISTER_VIEW_URL = "/fxml/RegisterView.fxml";
    private static final String MAIN_VIEW_URL = "/fxml/MainView.fxml";
    private static final String REGULAR_USER_VIEW_URL = "/fxml/RegularUserView.fxml";
    private static final String TRAVEL_AGENCY_PACKAGES_VIEW_URL = "/fxml/TravelAgencyPackagesView.fxml";
    private static final String ADD_PACKAGE_VIEW_URL = "/fxml/AddPackageView.fxml";
    private static final String EDIT_PACKAGE_VIEW_URL = "/fxml/EditPackageView.fxml";
    private static final String TRAVEL_AGENCY_DESTINATIONS_VIEW_URL = "/fxml/TravelAgencyDestinationsView.fxml";

    private static HashMap<String, Object> viewToController = new HashMap<>();

    public void openLoginView() throws IOException {
        openView(LOGIN_VIEW_URL, "Login");
    }

    public void openRegisterView() throws IOException {
        openView(REGISTER_VIEW_URL, "Register").setOnCloseRequest(e -> {
            try {
                openLoginView();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void openMainView() throws IOException {
        openView(MAIN_VIEW_URL, "Main").setOnCloseRequest(e -> Platform.exit());
    }

    public void openRegularUserView() throws IOException {
        openView(REGULAR_USER_VIEW_URL, "Home (Regular User)")
                .setOnCloseRequest(e -> {
                    RegularUserController regularUserController = (RegularUserController) getController(REGULAR_USER_VIEW_URL);
                    regularUserController.onClose();
                    Platform.exit();
                });
        RegularUserController regularUserController = (RegularUserController) getController(REGULAR_USER_VIEW_URL);
        regularUserController.init();
    }

    public void openTravelAgencyPackagesView() throws IOException {
        openView(TRAVEL_AGENCY_PACKAGES_VIEW_URL, "Home (Travel agency)").setOnCloseRequest(e -> {
                    TravelAgencyPackagesController travelAgencyPackagesController = (TravelAgencyPackagesController) getController(TRAVEL_AGENCY_PACKAGES_VIEW_URL);
                    travelAgencyPackagesController.onClose();
                    Platform.exit();
                }
        );
        TravelAgencyPackagesController travelAgencyPackagesController = (TravelAgencyPackagesController) getController(TRAVEL_AGENCY_PACKAGES_VIEW_URL);
        travelAgencyPackagesController.init();
    }

    public void openAddPackageView() throws IOException {
        openView(ADD_PACKAGE_VIEW_URL, "Add package");
        AddPackageController addPackageController = (AddPackageController) getController(ADD_PACKAGE_VIEW_URL);
        addPackageController.init();
    }

    public void openTravelAgencyDestinationsView() throws IOException {
        openView(TRAVEL_AGENCY_DESTINATIONS_VIEW_URL, "Vacation destinations (Travel agency)").setOnCloseRequest(e -> {
                    TravelAgencyDestinationsController travelAgencyDestinationsController = (TravelAgencyDestinationsController) getController(TRAVEL_AGENCY_DESTINATIONS_VIEW_URL);
                    travelAgencyDestinationsController.onClose();
                }
        );
        TravelAgencyDestinationsController travelAgencyDestinationsController = (TravelAgencyDestinationsController) getController(TRAVEL_AGENCY_DESTINATIONS_VIEW_URL);
        travelAgencyDestinationsController.init();
    }

    public void openEditPackageView(VacationPackageDTO vacationPackageDTO) throws IOException {
        openView(EDIT_PACKAGE_VIEW_URL, "Edit package");
        EditPackageController editPackageController = (EditPackageController) getController(EDIT_PACKAGE_VIEW_URL);
        editPackageController.init(vacationPackageDTO);
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

    private Object getController(String url) {
        return viewToController.get(url);
    }
}
