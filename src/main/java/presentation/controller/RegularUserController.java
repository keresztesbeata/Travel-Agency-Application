package presentation.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import service.dto.VacationPackageDTO;
import service.facade.RegularUserServiceFacade;
import service.roles.RegularUserRole;

import java.io.IOException;

public class RegularUserController {
    public TableView<VacationPackageDTO> vacationPackagesTable;

    private RegularUserRole userRole = new RegularUserServiceFacade();
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    public void onLogOut(ActionEvent actionEvent) {
        try {
            userRole.logout();
            viewLoaderFactory.openMainView();
            closeRegularUserView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onFilter(ActionEvent actionEvent) {
        // TODO
    }

    public void onCreateBooking(ActionEvent actionEvent) {
    }

    public void onShowAvailablePackages(ActionEvent actionEvent) {

    }

    public void onShowBookingsOfUser(ActionEvent actionEvent) {
    }

    private void closeRegularUserView() {
        Stage stage = (Stage) vacationPackagesTable.getScene().getWindow();
        stage.close();
    }
}
