package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.facade.TravelAgencyServiceFacade;
import service.roles.TravelAgencyRole;

public class AddPackageController {
    public Label errorLabel;
    public TextField nameField;
    public ComboBox<String> destinationComboBox;
    public TextArea detailsField;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextField maxNrBookingsField;
    public TextField priceField;

    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();
    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    public void onAddPackage() {
        try{
            VacationPackageDTO vacationPackageDTO = new VacationPackageDTO();
            vacationPackageDTO.setName(nameField.getText());
            vacationPackageDTO.setDetails(detailsField.getText());
            vacationPackageDTO.setVacationDestinationName(destinationComboBox.getSelectionModel().getSelectedItem());
            vacationPackageDTO.setPrice(Double.parseDouble(priceField.getText()));
            vacationPackageDTO.setMaxNrOfBookings(Integer.parseInt(maxNrBookingsField.getText()));
            vacationPackageDTO.setFrom(fromDatePicker.getValue());
            vacationPackageDTO.setTo(toDatePicker.getValue());
            userRole.addVacationPackage(vacationPackageDTO);
            closeView();
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
            nameField.clear();
            destinationComboBox.getSelectionModel().clearSelection();
            detailsField.clear();
            fromDatePicker.getEditor().clear();
            toDatePicker.getEditor().clear();
            maxNrBookingsField.clear();
            priceField.clear();
        }
    }

    private void closeView() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
