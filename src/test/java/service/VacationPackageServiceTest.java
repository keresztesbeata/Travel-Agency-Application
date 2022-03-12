package service;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class VacationPackageServiceTest {

    private static VacationPackageService vacationPackageService = new VacationPackageService();
    private static VacationDestinationService vacationDestinationService = new VacationDestinationService();
    private static VacationPackage.VacationPackageBuilder basePackageBuilder;
    private static VacationDestination vacationDestination;
    private static LocalDate startDate;
    private static LocalDate endDate;

    @BeforeAll
    static void prepareData() {
        vacationDestination = vacationDestinationService.findByName("Tuscany");
        startDate = LocalDate.of(2022, 7, 20);
        endDate = LocalDate.of(2022, 7, 27);
        basePackageBuilder = new VacationPackage
                .VacationPackageBuilder()
                .withDestination(vacationDestination)
                .withDetails("Pizza, pasta, and a glass of good italian wine, the heart of the italian cuisine.")
                .withNrOfPeople(5)
                .withPrice(2000d);
    }

    @Order(value = 1)
    @Test
    public void add() {
        VacationPackage validPackage = basePackageBuilder
                .withName("Trip to Tuscany")
                .withPeriod(startDate, endDate)
                .build();
        Assertions.assertDoesNotThrow(() -> vacationPackageService.add(validPackage));

        VacationPackage invalidPackage = basePackageBuilder
                .withName("Weekend in Italy")
                .withPeriod(endDate, startDate)
                .build();
        Assertions.assertThrows(InvalidInputException.class, () -> vacationPackageService.add(invalidPackage));
    }

    @Order(value = 2)
    @Test
    public void findBy() {
        List<VacationPackage> all = vacationPackageService.findAll();

        vacationPackageService.withPackageStatusFilter(Arrays.asList(PackageStatus.NOT_BOOKED, PackageStatus.BOOKED, PackageStatus.IN_PROGRESS));
        vacationPackageService.withContainsNameFilter("Trip");
        vacationPackageService.preparePeriodFilter(startDate.minusDays(2), endDate.plusDays(4));
        vacationPackageService.withPriceFilter(1000d, 4000d);
        List<VacationPackage> vacationPackages = vacationPackageService.filter();

        Assertions.assertTrue(vacationPackages.size() < all.size());
    }

    @Order(value = 3)
    @Test
    public void edit() {
        VacationPackage vacationPackage = basePackageBuilder
                .withName("Holiday in Hawaii")
                .withPeriod(startDate, endDate)
                .build();
        Assertions.assertDoesNotThrow(() -> vacationPackageService.edit(vacationPackage));

        vacationPackage.setName("Trip to Tuscany");
        Assertions.assertThrows(InvalidInputException.class, () -> vacationPackageService.edit(vacationPackage));
    }

    @Order(value = 4)
    @Test
    public void delete() {
        String invalidName = "Trip to Pisa";
        String validName = "Visit Tuscany";

        Assertions.assertThrows(InvalidOperationException.class, () -> vacationPackageService.delete(invalidName));
        Assertions.assertDoesNotThrow(() -> vacationPackageService.delete(validName));
        Assertions.assertNull(vacationPackageService.findByName(validName));
    }

    @Order(value = 5)
    @Test
    void deleteCascading() {
        String destinationName = "Tuscany";
        List<VacationPackage> allPackages = vacationPackageService.findAll();

        Assertions.assertDoesNotThrow(() -> vacationDestinationService.delete(destinationName));

        vacationPackageService.withDestinationFilter(destinationName);
        List<VacationPackage> remainingVacationPackages = vacationPackageService.filter();

        Assertions.assertTrue(remainingVacationPackages.stream()
                .noneMatch(vacationPackage -> vacationPackage.getVacationDestination().getName().equals(destinationName)));
        Assertions.assertTrue(remainingVacationPackages.size() < allPackages.size());
    }
}