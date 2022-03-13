package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import presentation.views.TableManager;
import presentation.views.UIComponentsFactory;
import repository.FilterConditions;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidOperationException;
import service.facade.RegularUserServiceFacade;
import service.roles.RegularUserRole;

import java.io.IOException;

public class RegularUserController {
    public TableView<VacationPackageDTO> vacationPackagesTable;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextField maxPriceField;
    public TextField minPriceField;
    public ComboBox<String> destinationComboBox;

    private RegularUserRole userRole = new RegularUserServiceFacade();
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TableManager tableManager;

    public void init() {
        tableManager = new TableManager(vacationPackagesTable);
        destinationComboBox.getItems().setAll(userRole.findAllDestinations());
        reload();
    }

    public void onLogOut() {
        try {
            userRole.logout();
            viewLoaderFactory.openMainView();
            closeRegularUserView();
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
            reload();
        } catch (InvalidOperationException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error! Failed to book package.", e.getMessage())
                    .showAndWait();
        }
    }

    public void onShowAvailablePackages() {
        reload();
    }

    public void onShowBookingsOfUser() {
        tableManager.updateTable(userRole.findBookedVacationPackagesOfCurrentUser());
    }

    private void closeRegularUserView() {
        Stage stage = (Stage) vacationPackagesTable.getScene().getWindow();
        stage.close();
    }

    public void onApplyFilters() {
        FilterConditions.FilterConditionsBuilder builder = new FilterConditions.FilterConditionsBuilder();
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
        FilterConditions filterConditions = builder.build();
        tableManager.updateTable(userRole.filterVacationPackagesByConditions(filterConditions));
    }

    public void onClearFilters() {
        fromDatePicker.getEditor().clear();
        toDatePicker.getEditor().clear();
        minPriceField.clear();
        maxPriceField.clear();
        destinationComboBox.getSelectionModel().clearSelection();
    }

    private void reload() {
        tableManager.updateTable(userRole.findAvailableVacationPackages());
    }
}
