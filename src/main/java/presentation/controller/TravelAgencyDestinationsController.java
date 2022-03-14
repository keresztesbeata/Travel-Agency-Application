package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import model.VacationDestination;
import presentation.views.UIComponentsFactory;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.facade.TravelAgencyServiceFacade;
import service.facade.roles.TravelAgencyRole;
import service.managers.AbstractManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class TravelAgencyDestinationsController implements PropertyChangeListener {
    public ListView<String> vacationDestinationsList;
    public TextField destinationNameField;

    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    public void init() {
        vacationDestinationsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        vacationDestinationsList.setPlaceholder(new Label("No destinations loaded yet."));
        userRole.registerListener(this);
        onReload();
    }

    public void onClose() {
        userRole.removeListener(this);
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
        closeView();
    }

    public void onViewPackages() {
        closeView();
    }

    private void closeView() {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(AbstractManager.PROPERTY_CHANGE.ADDED_ENTRY.name()) ||
                evt.getPropertyName().equals(AbstractManager.PROPERTY_CHANGE.DELETED_ENTRY.name())) {
            onReload();
        }
    }
}
