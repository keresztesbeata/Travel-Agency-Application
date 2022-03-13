package service.roles;

import model.User;
import model.VacationPackage;
import repository.FilterConditions;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.util.List;

public interface RegularUserRole extends UserRole {
    void register(User user) throws InvalidInputException;

    void bookVacationPackage(VacationPackage vacationPackage) throws InvalidOperationException;

    List<VacationPackage> findBookedVacationPackagesOfCurrentUser();

    List<VacationPackage> findAvailableVacationPackages();

    VacationPackage findVacationPackageByName(String vacationPackageName);

    List<VacationPackage> filterVacationPackagesByConditions(FilterConditions filterConditions);
}
