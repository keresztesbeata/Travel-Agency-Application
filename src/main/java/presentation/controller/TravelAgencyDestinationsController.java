package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import model.VacationDestination;
import presentation.views.UIComponentsFactory;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.facade.TravelAgencyServiceFacade;
import service.roles.TravelAgencyRole;

import java.io.IOException;
import java.util.List;

public class TravelAgencyDestinationsController {
    public ListView<String> vacationDestinationsList;
    public TextField destinationNameField;

    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    public void init() {
        vacationDestinationsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        vacationDestinationsList.setPlaceholder(new Label("No destinations loaded yet."));
        onReload();
    }

    public void onReload() {
        updateTable(userRole.findAllDestinations());
    }

    public void updateTable(List<String> data) {
        vacationDestinationsList.getItems().clear();
        if (data == null || data.isEmpty()) {
            vacationDestinationsList.setPlaceholder(new Label("No destinations found."));
        } else {
            vacationDestinationsList.getItems().setAll(data);
        }
    }

    public void onAddNewDestination() {
        String destinationName = destinationNameField.getText();
        try {
            userRole.addVacationDestination(destinationName);
        } catch (InvalidInputException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error adding new destination!", e.getMessage())
                    .showAndWait();
        }
    }

    public void onDeleteDestination() {
        String destinationName = vacationDestinationsList.getSelectionModel().getSelectedItem();
        try {
            userRole.deleteVacationDestination(destinationName);
        } catch (InvalidOperationException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error deleting destination!", e.getMessage())
                    .showAndWait();
        }
    }

    public void onLogOut() {
        userRole.logout();
        try {
            viewLoaderFactory.openMainView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeTravelAgencyView();
    }


    public void onViewPackages() {
        try {
            viewLoaderFactory.openTravelAgencyPackagesView();
            closeTravelAgencyView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeTravelAgencyView() {
        Stage stage = (Stage) vacationDestinationsList.getScene().getWindow();
        stage.close();
    }

    public void onSearchDestination() {
        String destinationName = destinationNameField.getText();
        VacationDestination vacationDestination = userRole.findDestinationByName(destinationName);
        if (vacationDestination != null) {
            vacationDestinationsList.getSelectionModel().select(vacationDestination.getName());
        }
    }
}
