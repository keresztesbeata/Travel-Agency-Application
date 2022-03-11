package service;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class VacationPackageServiceTest {

    @InjectMocks
    VacationPackageService vacationPackageService = new VacationPackageService();

    @InjectMocks
    VacationDestinationService vacationDestinationService = new VacationDestinationService();

    private final String SAMPLE_DESTINATION_NAME_1 = "Florence";
    private final String SAMPLE_DESTINATION_NAME_0 = "Venice";
    private final String SAMPLE_PACKAGE_NAME_0 = "Trip to Italy";
    private final String SAMPLE_PACKAGE_NAME_1 = "Trip to North of Italy";
    private final String SAMPLE_DETAIL = "5 star accommodation";

    @Test
    void testCRUDOperations() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME_0);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));

        LocalDate startDate = LocalDate.of(2022, 7, 20);
        LocalDate endDate = LocalDate.of(2022, 7, 27);
        VacationPackage vacationPackage = new VacationPackage
                .VacationPackageBuilder()
                .withName(SAMPLE_PACKAGE_NAME_0)
                .withDestination(vacationDestination)
                .withDetails(SAMPLE_DETAIL)
                .withNrOfPeople(5L)
                .withPeriod(startDate, endDate)
                .withPrice(2000d)
                .build();

        LocalDate startDate1 = LocalDate.of(2022, 10, 20);
        LocalDate endDate1 = LocalDate.of(2022, 10, 27);
        VacationPackage vacationPackage1 = new VacationPackage
                .VacationPackageBuilder()
                .withName(SAMPLE_PACKAGE_NAME_1)
                .withDestination(vacationDestination)
                .withDetails(SAMPLE_DETAIL)
                .withNrOfPeople(5L)
                .withPeriod(startDate1, endDate1)
                .withPrice(2000d)
                .build();

        vacationPackage.setPackageStatus(PackageStatus.BOOKED);

        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(vacationPackage));
        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(vacationPackage1));

        Assertions.assertNotNull(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME_0));
        Assertions.assertFalse(vacationPackageService.findByPrice(1500d, 3000d).isEmpty());
        Assertions.assertFalse(vacationPackageService.findByDestinationName(SAMPLE_DESTINATION_NAME_0).isEmpty());
        Assertions.assertFalse(vacationPackageService.findByPeriod(startDate.minusDays(3), endDate.plusDays(2)).isEmpty());

        List<PackageStatus> statusList = new ArrayList<>();
        statusList.add(PackageStatus.BOOKED);
        statusList.add(PackageStatus.NOT_BOOKED);
        Assertions.assertEquals(2,vacationPackageService.findByPackageStatus(statusList).size());

        Assertions.assertEquals(2,vacationPackageService.findAll().size());

        double newPrice = 5000d;
        vacationPackage.setPrice(newPrice);
        Assertions.assertDoesNotThrow(() -> vacationPackageService.edit(vacationPackage));
        Assertions.assertEquals(vacationPackageService.findByName(SAMPLE_PACKAGE_NAME_0).getPrice(), newPrice);

        Assertions.assertDoesNotThrow(() -> vacationPackageService.delete(SAMPLE_PACKAGE_NAME_0));
    }

}