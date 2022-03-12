package service.roles;

import model.User;
import model.VacationPackage;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.time.LocalDate;
import java.util.List;

public interface RegularUserRole extends UserRole {
    void register(User user) throws InvalidInputException;

    void bookVacationPackage(VacationPackage vacationPackage) throws InvalidOperationException;

    List<VacationPackage> findBookedVacationPackagesOfCurrentUser();

    List<VacationPackage> findAvailableVacationPackages();

    VacationPackage findVacationPackageByName(String vacationPackageName);

    List<VacationPackage> filterVacationPackagesByDestination(String destination);

    List<VacationPackage> filterVacationPackagesByPeriod(LocalDate startDate, LocalDate endDate);

    List<VacationPackage> filterVacationPackagesByPrice(Double minPrice, Double maxPrice);

    List<VacationPackage> filterVacationPackagesByKeyword(String keyword);
}
