package service;

import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;

import java.time.LocalDate;
import java.util.List;

class VacationPackageManagerTest {

    private static VacationPackageManager vacationPackageManager = new VacationPackageManager();
    private static VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();
    private static VacationPackage.VacationPackageBuilder basePackageBuilder;
    private static VacationDestination vacationDestination;
    private static LocalDate startDate;
    private static LocalDate endDate;

    @BeforeAll
    static void prepareData() {
        vacationDestination = vacationDestinationManager.findByName("Tuscany");
        startDate = LocalDate.of(2022, 7, 20);
        endDate = LocalDate.of(2022, 7, 27);
        basePackageBuilder = new VacationPackage
                .VacationPackageBuilder()
                .withDestination(vacationDestination)
                .withDetails("Pizza, pasta, and a glass of good italian wine, the heart of the italian cuisine.")
                .withMaxCapacity(5)
                .withPrice(2000d);
    }

    @Order(value = 1)
    @Test
    public void add() {
        VacationPackage validPackage = basePackageBuilder
                .withName("Trip to Tuscany")
                .withPeriod(startDate, endDate)
                .build();
        Assertions.assertDoesNotThrow(() -> vacationPackageManager.add(validPackage));

        VacationPackage invalidPackage = basePackageBuilder
                .withName("Weekend in Italy")
                .withPeriod(endDate, startDate)
                .build();
        Assertions.assertThrows(InvalidInputException.class, () -> vacationPackageManager.add(invalidPackage));
    }

    @Order(value = 3)
    @Test
    public void edit() {
        VacationPackage vacationPackage = basePackageBuilder
                .withName("Holiday in Hawaii")
                .withPeriod(startDate, endDate)
                .build();
        Assertions.assertDoesNotThrow(() -> vacationPackageManager.edit(vacationPackage));

        vacationPackage.setName("Trip to Tuscany");
        Assertions.assertThrows(InvalidInputException.class, () -> vacationPackageManager.edit(vacationPackage));
    }

    @Order(value = 4)
    @Test
    public void delete() {
        String invalidName = "Trip to Pisa";
        String validName = "Visit Tuscany";

        Assertions.assertThrows(InvalidOperationException.class, () -> vacationPackageManager.delete(invalidName));
        Assertions.assertDoesNotThrow(() -> vacationPackageManager.delete(validName));
        Assertions.assertNull(vacationPackageManager.findByName(validName));
    }

    @Order(value = 5)
    @Test
    void deleteCascading() {
        String destinationName = "Tuscany";
        List<VacationPackage> allPackages = vacationPackageManager.findAll();
        Assertions.assertDoesNotThrow(() -> vacationDestinationManager.delete(destinationName));
    }
}