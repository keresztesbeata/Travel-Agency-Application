package service.facade;

import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.exceptions.InvalidInputException;
import service.managers.VacationDestinationManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TravelAgencyServiceFacadeTest {

    private TravelAgencyServiceFacade travelAgencyServiceFacade = new TravelAgencyServiceFacade();
    private VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();

    @Test
    void addVacationPackageForDestination() {
        LocalDate startDate = LocalDate.of(2022, 7, 20);
        LocalDate endDate = LocalDate.of(2022, 7, 27);
        VacationPackage.VacationPackageBuilder vacationPackageBuilder = new VacationPackage
                .VacationPackageBuilder()
                .withDetails("Pizza, pasta, and a glass of good italian wine, the heart of the italian cuisine.")
                .withMaxCapacity(5)
                .withPrice(2000d)
                .withName("Second trip to Venice")
                .withPeriod(startDate, endDate);

        String validVacationDestinationName = "Venice";
        VacationDestination validVacationDestination = vacationDestinationManager.findByName(validVacationDestinationName);
        VacationPackage validPackage = vacationPackageBuilder
                .withDestination(validVacationDestination)
                .build();

        Assertions.assertDoesNotThrow(() -> travelAgencyServiceFacade.addVacationPackageForDestination(validPackage, validVacationDestinationName));

        String inValidVacationDestinationName = "Tscany";
        VacationPackage inValidPackage = vacationPackageBuilder
                .withDestination(validVacationDestination)
                .build();

        Assertions.assertThrows(InvalidInputException.class, () -> travelAgencyServiceFacade.addVacationPackageForDestination(inValidPackage, inValidVacationDestinationName));

    }

}