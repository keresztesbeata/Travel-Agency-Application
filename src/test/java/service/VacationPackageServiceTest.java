package service;

import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;

class VacationPackageServiceTest {

    @InjectMocks
    VacationPackageService vacationPackageService = new VacationPackageService();

    @InjectMocks
    VacationDestinationService vacationDestinationService = new VacationDestinationService();

    private final String SAMPLE_DESTINATION_NAME = "Venice";
    private final String SAMPLE_PACKAGE_NAME = "Trip to Italy";

    @Test
    void testCRUDOperations() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));

        VacationPackage vacationPackage = new VacationPackage
                .VacationPackageBuilder()
                .withName(SAMPLE_PACKAGE_NAME)
                .withDestination(vacationDestination)
                .withDetails("5 star accomodation")
                .withNrOfPeople(5L)
                .withPeriod(LocalDate.of(2022,7,20),LocalDate.of(2022,7,27))
                .withPrice(2000d)
                .build();

        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(vacationPackage));
        Assertions.assertNotNull(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME));
        Assertions.assertFalse(vacationPackageService.findByPrice(1500d,3000d).isEmpty());

        double newPrice = 5000d;
        vacationPackage.setPrice(newPrice);
        Assertions.assertDoesNotThrow(() -> vacationPackageService.edit(vacationPackage));
        Assertions.assertEquals(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME).getPrice(), newPrice);
    }
}