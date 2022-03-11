package service;

import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.List;

class VacationPackageServiceTest {

    @InjectMocks
    VacationPackageService vacationPackageService = new VacationPackageService();

    @InjectMocks
    VacationDestinationService vacationDestinationService = new VacationDestinationService();

    private final String SAMPLE_DESTINATION_NAME = "Venice";
    private final String SAMPLE_PACKAGE_NAME = "Trip to Italy";
    private final String SAMPLE_DETAIL = "5 star accommodation";

    @Test
    void testCRUDOperations() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));

        LocalDate startDate = LocalDate.of(2022, 7, 20);
        LocalDate endDate = LocalDate.of(2022, 7, 27);
        VacationPackage vacationPackage = new VacationPackage
                .VacationPackageBuilder()
                .withName(SAMPLE_PACKAGE_NAME)
                .withDestination(vacationDestination)
                .withDetails(SAMPLE_DETAIL)
                .withNrOfPeople(5L)
                .withPeriod(startDate, endDate)
                .withPrice(2000d)
                .build();

        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(vacationPackage));

        Assertions.assertNotNull(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME));
        Assertions.assertFalse(vacationPackageService.findByPrice(1500d, 3000d).isEmpty());
        Assertions.assertFalse(vacationPackageService.findByDestinationName(SAMPLE_DESTINATION_NAME).isEmpty());
        Assertions.assertFalse(vacationPackageService.findByPeriod(startDate.plusDays(2), endDate.minusDays(3)).isEmpty());

        double newPrice = 5000d;
        vacationPackage.setPrice(newPrice);
        Assertions.assertDoesNotThrow(() -> vacationPackageService.edit(vacationPackage));
        Assertions.assertEquals(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME).getPrice(), newPrice);

        Assertions.assertDoesNotThrow(() -> vacationPackageService.delete(SAMPLE_PACKAGE_NAME));
        Assertions.assertNull(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME));
    }
}