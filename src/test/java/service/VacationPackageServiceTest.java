package service;

import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;

class VacationPackageServiceTest {

    @InjectMocks
    VacationPackageService vacationPackageService = new VacationPackageService();

    @InjectMocks
    VacationDestinationService vacationDestinationService = new VacationDestinationService();

    private final String SAMPLE_DESTINATION_NAME = "Zagreb";

    @Test
    void add() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));

        VacationPackage vacationPackage = new VacationPackage
                .VacationPackageBuilder()
                .withName("Trip to Croatia")
                .withDestination(vacationDestination)
                .withDetails("5 star accomodation")
                .withNrOfPeople(5L)
                .withPeriod(LocalDate.of(2022,7,20),LocalDate.of(2022,7,27))
                .withPrice(2000d)
                .build();

        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(vacationPackage));
    }

    @Test
    void delete() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByDestination() {

    }

    @Test
    void findByPrice() {
    }

    @Test
    void edit() {
    }
}