package service.validators;

import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;

import java.time.LocalDate;

public class VacationPackageValidator implements InputValidator<VacationPackageDTO> {

    @Override
    public void validate(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        validatePackageName(vacationPackageDTO.getName());
        validatePeriod(vacationPackageDTO.getFrom(), vacationPackageDTO.getTo());
        validatePrice(vacationPackageDTO.getPrice());
        validateNrOfPeople(vacationPackageDTO.getMaxNrOfBookings());
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

    private void validateNrOfPeople(Integer nrOfPeople) throws InvalidInputException {
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
