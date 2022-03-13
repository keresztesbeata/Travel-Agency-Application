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
        validateMaxNrOfBookings(vacationPackageDTO.getMaxNrOfBookings());
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) throws InvalidInputException {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
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

    private void validateMaxNrOfBookings(Integer maxNrOfBookings) throws InvalidInputException {
        if (maxNrOfBookings == null) {
            throw new InvalidInputException("The max nr of bookings cannot be a missing!");
        }
        if (maxNrOfBookings < 0) {
            throw new InvalidInputException("The max nr of bookings cannot be a negative value!");
        }
    }

    private void validatePackageName(String name) throws InvalidInputException {
        if (name == null) {
            throw new InvalidInputException("The name of the destination should not be empty!");
        }
    }
}
