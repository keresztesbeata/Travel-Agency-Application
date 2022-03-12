package service.roles;

import model.User;
import model.VacationPackage;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.util.List;

public interface RegularUserRole extends UserRole{
    void register(User user) throws InvalidInputException;
    void bookVacationPackage(VacationPackage vacationPackage) throws InvalidOperationException;
    void findBookedVacationPackagesByUser(User user);
    List<VacationPackage> findAvailableVacationPackages();
}
