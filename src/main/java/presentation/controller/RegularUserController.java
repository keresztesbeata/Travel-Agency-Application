package presentation.controller;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.views.TableManager;
import presentation.views.UIComponentsFactory;
import repository.FilterConditions;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidOperationException;
import service.facade.RegularUserServiceFacade;
import service.facade.roles.RegularUserRole;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class RegularUserController implements PropertyChangeListener {
    public TableView<VacationPackageDTO> vacationPackagesTable;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextField maxPriceField;
    public TextField minPriceField;
    public ComboBox<String> destinationComboBox;
    public Button applyFiltersButton;
    public Button clearFiltersButton;
    public VBox createBookingBox;
    public TextField keywordField;

    private RegularUserRole userRole = new RegularUserServiceFacade();
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TableManager tableManager;

    public void init() {
        tableManager = new TableManager(vacationPackagesTable);
        destinationComboBox.getItems().setAll(userRole.findAllDestinations());
        userRole.registerListener(this);
        reload();
    }

    public void onClose() {
        userRole.removeListener(this);
    }

    public void onLogOut() {
        try {
            userRole.logout();
            viewLoaderFactory.openMainView();
            closeView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCreateBooking() {
        VacationPackageDTO vacationPackageDTO = tableManager.getTable().getSelectionModel().getSelectedItem();
        try {
            userRole.bookVacationPackage(vacationPackageDTO);
            UIComponentsFactory.getAlertDialog(Alert.AlertType.INFORMATION, "Success!", "Package was booked successfully!")
                    .showAndWait();
        } catch (InvalidOperationException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error! Failed to book package.", e.getMessage())
                    .showAndWait();
        }
    }

    public void onShowAvailablePackages() {
        createBookingBox.setVisible(true);
        applyFiltersButton.setDisable(false);
        clearFiltersButton.setDisable(false);
        reload();
    }

    public void onShowBookingsOfUser() {
        createBookingBox.setVisible(false);
        applyFiltersButton.setDisable(true);
        clearFiltersButton.setDisable(true);
        tableManager.updateTable(userRole.findBookedVacationPackagesOfCurrentUser());
    }

    private void closeView() {
        Stage stage = (Stage) vacationPackagesTable.getScene().getWindow();
        stage.close();
    }

    public void onApplyFilters() {
        FilterConditions.FilterConditionsBuilder builder = new FilterConditions.FilterConditionsBuilder();
        builder = builder.withAvailability(true);
        String destinationName = destinationComboBox.getSelectionModel().getSelectedItem();
        if (destinationName != null && !destinationName.isEmpty()) {
            builder = builder.withDestinationName(destinationName);
        }
        if (fromDatePicker.getValue() != null) {
            builder = builder.withStartDate(fromDatePicker.getValue());
        }
        if (toDatePicker.getValue() != null) {
            builder = builder.withEndDate(toDatePicker.getValue());
        }
        if (minPriceField.getText() != null && !minPriceField.getText().isEmpty()) {
            builder = builder.withMinPrice(Double.parseDouble(minPriceField.getText()));
        }
        if (maxPriceField.getText() != null && !maxPriceField.getText().isEmpty()) {
            builder = builder.withMaxPrice(Double.parseDouble(maxPriceField.getText()));
        }
        if(keywordField.getText() != null && !keywordField.getText().isEmpty()) {
            builder = builder.withKeyword(keywordField.getText());
        }
        FilterConditions filterConditions = builder.build();
        tableManager.updateTable(userRole.filterVacationPackagesByConditions(filterConditions));
    }

    public void onClearFilters() {
        fromDatePicker.getEditor().clear();
        toDatePicker.getEditor().clear();
        minPriceField.clear();
        maxPriceField.clear();
        destinationComboBox.getSelectionModel().clearSelection();
        keywordField.clear();
    }

    private void reload() {
        tableManager.updateTable(userRole.findAvailableVacationPackages());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        reload();
    }
}
