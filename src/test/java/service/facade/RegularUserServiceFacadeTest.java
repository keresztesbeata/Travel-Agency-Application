package service.facade;

import model.PackageStatus;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.VacationPackageFilterModel;
import service.exceptions.InvalidInputException;
import service.managers.UserManager;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static model.UserType.REGULAR_USER;
import static org.junit.jupiter.api.Assertions.*;

class RegularUserServiceFacadeTest {

    private RegularUserServiceFacade regularUserServiceFacade = new RegularUserServiceFacade();
    private static User user;

    @BeforeAll
    static void prepareData() {
        user = new User("wanda","Wanda@1",REGULAR_USER);
    }

    @Test
    void bookVacationPackage() {
        Assertions.assertDoesNotThrow(() -> regularUserServiceFacade.login(user));

        String validVacationDestinationName = "Venice";
        String almostFullyBookedVacationPackageName = "Visit London";
        String emptyPackageName = "Trip to Venice";

        List<VacationPackage> packageList = regularUserServiceFacade.findAvailableVacationPackages();
        Assertions.assertFalse(packageList.isEmpty());

        VacationPackage validPackage = packageList.get(0);
        Integer nrOfBookings = validPackage.getNrOfBookings();
        Assertions.assertDoesNotThrow(() -> regularUserServiceFacade.bookVacationPackage(validPackage));

        VacationPackage updatedPackage = regularUserServiceFacade.findVacationPackageByName(emptyPackageName);
        Assertions.assertEquals((int) updatedPackage.getNrOfBookings(), nrOfBookings + 1);
        Assertions.assertEquals(PackageStatus.IN_PROGRESS, updatedPackage.getPackageStatus());

        VacationPackage almostFullPackage = regularUserServiceFacade.findVacationPackageByName(almostFullyBookedVacationPackageName);
        Assertions.assertDoesNotThrow(() -> regularUserServiceFacade.bookVacationPackage(almostFullPackage));
        Assertions.assertEquals((int)almostFullPackage.getMaxNrOfBookings(), (int) almostFullPackage.getNrOfBookings());
        Assertions.assertEquals(PackageStatus.BOOKED,almostFullPackage.getPackageStatus());
    }

    @Test
    void findBookedVacationPackagesOfCurrentUser() {
        Assertions.assertDoesNotThrow(() -> regularUserServiceFacade.login(user));

        System.out.println(regularUserServiceFacade.getCurrentUser());
       // List<VacationPackage> vacationPackageList = regularUserServiceFacade.findBookedVacationPackagesOfCurrentUser();
       // User currentUser = regularUserServiceFacade.getCurrentUser();
       // vacationPackageList.forEach(System.out::println);
       // Assertions.assertTrue(vacationPackageList.stream().allMatch(vacationPackage -> vacationPackage.getUsers().contains(currentUser)));
    }

    @Test
    void findAvailableVacationPackages() {
        Assertions.assertDoesNotThrow(() -> regularUserServiceFacade.login(user));

        List<VacationPackage> vacationPackageList = regularUserServiceFacade.findAvailableVacationPackages();
        Assertions.assertTrue(vacationPackageList.stream().noneMatch(vacationPackage -> vacationPackage.getPackageStatus().equals(PackageStatus.BOOKED)));
    }
}