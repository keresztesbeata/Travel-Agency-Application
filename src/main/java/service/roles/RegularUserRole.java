package service.roles;

import model.User;
import model.VacationPackage;
import repository.FilterConditions;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.util.List;

public interface RegularUserRole extends UserRole {
    void register(User user) throws InvalidInputException;

    void bookVacationPackage(VacationPackageDTO vacationPackage) throws InvalidOperationException;

    List<VacationPackageDTO> findBookedVacationPackagesOfCurrentUser();

    List<VacationPackageDTO> findAvailableVacationPackages();

    VacationPackageDTO findVacationPackageByName(String vacationPackageName);

    List<VacationPackageDTO> filterVacationPackagesByConditions(FilterConditions filterConditions);

    List<String> findAllDestinations();
}
