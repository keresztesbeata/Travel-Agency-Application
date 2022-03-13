package presentation.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.facade.TravelAgencyServiceFacade;
import service.roles.TravelAgencyRole;

public class EditPackageController {
    public Label errorLabel;
    public TextField nameField;
    public ComboBox<String> destinationComboBox;
    public TextArea detailsField;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextField maxNrBookingsField;
    public TextField priceField;

    private TravelAgencyRole userRole = new TravelAgencyServiceFacade();

    private VacationPackageDTO originalVacationPackageDTO;

    public void init(VacationPackageDTO originalVacationPackageDTO) {
        this.originalVacationPackageDTO = originalVacationPackageDTO;
        errorLabel.setText("");
        initFields();
    }

    public void onUpdatePackage() {
        try {
            VacationPackageDTO vacationPackageDTO = new VacationPackageDTO();
            vacationPackageDTO.setName(nameField.getText());
            vacationPackageDTO.setDetails(detailsField.getText());
            vacationPackageDTO.setVacationDestinationName(destinationComboBox.getSelectionModel().getSelectedItem());
            vacationPackageDTO.setPrice(Double.parseDouble(priceField.getText()));
            vacationPackageDTO.setMaxNrOfBookings(Integer.parseInt(maxNrBookingsField.getText()));
            vacationPackageDTO.setFrom(fromDatePicker.getValue());
            vacationPackageDTO.setTo(toDatePicker.getValue());
            userRole.editVacationPackage(vacationPackageDTO);
            closeView();
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
            initFields();
        }
    }

    private void initFields() {
        nameField.setText(originalVacationPackageDTO.getName());
        nameField.setEditable(false);
        destinationComboBox.getItems().addAll(userRole.findAllDestinations());
        destinationComboBox.getSelectionModel().select(originalVacationPackageDTO.getVacationDestinationName());
        detailsField.setText(originalVacationPackageDTO.getDetails());
        fromDatePicker.setValue(originalVacationPackageDTO.getFrom());
        toDatePicker.setValue(originalVacationPackageDTO.getTo());
        maxNrBookingsField.setText(originalVacationPackageDTO.getMaxNrOfBookings().toString());
        priceField.setText(originalVacationPackageDTO.getPrice().toString());
        System.out.println(originalVacationPackageDTO);
    }

    private void closeView() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
