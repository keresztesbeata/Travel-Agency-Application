package presentation.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.PackageStatus;
import presentation.views.TableManager;
import presentation.views.UIComponentsFactory;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidOperationException;
import service.exceptions.PackageBookedException;
import service.facade.TravelAgencyServiceFacade;
import service.roles.TravelAgencyRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TravelAgencyPackagesController {
    public TableView<VacationPackageDTO> vacationPackagesTable;
    public CheckBox notBookedChoiceBox;
    public CheckBox inProgressChoiceBox;
    public CheckBox bookedChoiceBox;

    private TableManager tableManager;
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    public void init() {
        vacationPackagesTable.setOnMouseClicked(mouseEvent -> {
            try {
                if(mouseEvent.getClickCount() > 1) {
                    // on double click
                    viewLoaderFactory.openEditPackageView(vacationPackagesTable.getSelectionModel().getSelectedItem());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tableManager = new TableManager(vacationPackagesTable);
        onReload();
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

    public void onFilterByStatus() {
        List<PackageStatus> statusList = new ArrayList<>();
        if (notBookedChoiceBox.isSelected()) {
            statusList.add(PackageStatus.NOT_BOOKED);
        }
        if (inProgressChoiceBox.isSelected()) {
            statusList.add(PackageStatus.IN_PROGRESS);
        }
        if (bookedChoiceBox.isSelected()) {
            statusList.add(PackageStatus.BOOKED);
        }
        tableManager.updateTable(userRole.filterVacationPackagesByStatus(statusList));
    }

    public void onReload() {
        tableManager.updateTable(userRole.filterVacationPackagesByStatus(Arrays.asList(PackageStatus.NOT_BOOKED, PackageStatus.IN_PROGRESS, PackageStatus.BOOKED)));
    }

    public void onAddNewPackage() {
        try {
            viewLoaderFactory.openAddPackageView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onViewDestinations() {
        try {
            viewLoaderFactory.openTravelAgencyDestinationsView();
            closeTravelAgencyView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDeletePackage() {
        VacationPackageDTO selectedItem = tableManager.getTable().getSelectionModel().getSelectedItem();
        try{
            userRole.deleteVacationPackage(selectedItem.getName());
        } catch (InvalidOperationException e) {
            UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error deleting package!", e.getMessage())
                    .showAndWait();
        } catch (PackageBookedException e) {
            Optional<ButtonType> result = UIComponentsFactory.getConfirmDialog("Alert!","Do you wish to delete the selected package? There mare some users who booked the given package. This operation cannot be undone!")
                    .showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    userRole.unSafeDeleteVacationPackage(selectedItem.getName());
                    UIComponentsFactory.getAlertDialog(Alert.AlertType.INFORMATION, "Success!","Package was deleted successfully!")
                            .showAndWait();
                } catch (InvalidOperationException ex) {
                    UIComponentsFactory.getAlertDialog(Alert.AlertType.ERROR, "Error deleting package!", e.getMessage())
                            .showAndWait();
                }
            }
        }
    }

    private void closeTravelAgencyView() {
        Stage stage = (Stage) vacationPackagesTable.getScene().getWindow();
        stage.close();
    }
}
