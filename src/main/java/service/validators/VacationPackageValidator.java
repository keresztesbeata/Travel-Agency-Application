package service.validators;

import model.VacationPackage;
import service.exceptions.InvalidInputException;

import java.time.LocalDate;

public class VacationPackageValidator implements InputValidator {
    private VacationPackage vacationPackage;

    public VacationPackageValidator(VacationPackage vacationPackage) {
        this.vacationPackage = vacationPackage;
    }

    @Override
    public void validate() throws InvalidInputException {
        validatePackageName(vacationPackage.getName());
        validatePeriod(vacationPackage.getStartDate(), vacationPackage.getEndDate());
        validatePrice(vacationPackage.getPrice());
        validateNrOfPeople(vacationPackage.getNrOfPeople());
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) throws InvalidInputException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidInputException("Invalid period! The start date cannot be after the end date!");
        }
    }

    private void validatePrice(Double price) throws InvalidInputException {
        if (price == null) {
            throw new InvalidInputException("The price cannot be a missing!");
        }
        if (price < 0) {
            throw new InvalidInputException("The price cannot be a negative value!");
        }
    }

    private void validateNrOfPeople(Long nrOfPeople) throws InvalidInputException {
        if (nrOfPeople == null) {
            throw new InvalidInputException("The nr of people cannot be a missing!");
        }
        if (nrOfPeople < 0) {
            throw new InvalidInputException("The nr of people cannot be a negative value!");
        }
    }

    private void validatePackageName(String name) throws InvalidInputException {
        if (name == null) {
            throw new InvalidInputException("The name of the destination should not be empty!");
        }
    }
}
