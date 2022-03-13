package presentation.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.PackageStatus;
import presentation.views.TableManager;
import service.dto.VacationPackageDTO;
import service.facade.TravelAgencyServiceFacade;
import service.roles.TravelAgencyRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TravelAgencyController {
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
                viewLoaderFactory.openEditPackageView(vacationPackagesTable.getSelectionModel().getSelectedItem());
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

    private void closeTravelAgencyView() {
        Stage stage = (Stage) vacationPackagesTable.getScene().getWindow();
        stage.close();
    }

    public void onAddNewPackage(ActionEvent actionEvent) {
        try {
            viewLoaderFactory.openAddPackageView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddNewDestination(ActionEvent actionEvent) {
    }
}
