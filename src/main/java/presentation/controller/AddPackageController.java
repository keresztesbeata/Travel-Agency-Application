package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.facade.TravelAgencyServiceFacade;
import service.facade.roles.TravelAgencyRole;

import java.time.LocalDate;

public class AddPackageController {
    public Label errorLabel;
    public TextField nameField;
    public ComboBox<String> destinationComboBox;
    public TextArea detailsField;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextField maxNrBookingsField;
    public TextField priceField;

    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    public void init() {
        errorLabel.setText("");
        destinationComboBox.getItems().addAll(userRole.findAllDestinations());
    }

    public void onAddPackage() {
        try{
            VacationPackageDTO vacationPackageDTO = new VacationPackageDTO();
            vacationPackageDTO.setName(nameField.getText());
            vacationPackageDTO.setDetails(detailsField.getText());
            vacationPackageDTO.setVacationDestinationName(destinationComboBox.getSelectionModel().getSelectedItem());
            if(priceField.getText() != null && !priceField.getText().isEmpty()) {
                try{
                    Double price = Double.parseDouble(priceField.getText());
                    vacationPackageDTO.setPrice(price);
                }catch(NumberFormatException e) {
                    errorLabel.setText("Invalid number for price!");
                    clearFields();
                }
            }
            if(maxNrBookingsField.getText() != null && !maxNrBookingsField.getText().isEmpty()) {
                try{
                    Integer maxNrOfBookings = Integer.parseInt(maxNrBookingsField.getText());
                    vacationPackageDTO.setMaxNrOfBookings(maxNrOfBookings);
                }catch(NumberFormatException e) {
                    errorLabel.setText("Invalid number for max nr of bookings!");
                    clearFields();
                }
            }
            if(fromDatePicker.getValue() != null) {
                vacationPackageDTO.setFrom(fromDatePicker.getValue());
            }
            if(toDatePicker.getValue() != null) {
                vacationPackageDTO.setTo(toDatePicker.getValue());
            }
            userRole.addVacationPackage(vacationPackageDTO);
            closeView();
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
            clearFields();
        }
    }

    private void clearFields() {
        nameField.clear();
        destinationComboBox.getSelectionModel().clearSelection();
        detailsField.clear();
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        maxNrBookingsField.clear();
        priceField.clear();
    }

    private void closeView() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
