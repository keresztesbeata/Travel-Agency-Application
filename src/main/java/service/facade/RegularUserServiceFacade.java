package service.facade;

import model.User;
import model.VacationPackage;
import repository.FilterConditions;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.UserManager;
import service.managers.VacationPackageManager;
import service.roles.RegularUserRole;

import java.util.List;

public class RegularUserServiceFacade extends UserService implements RegularUserRole {
    private UserManager userManager = new UserManager();
    private VacationPackageManager vacationPackageManager = new VacationPackageManager();

    @Override
    public void bookVacationPackage(VacationPackage vacationPackage) throws InvalidOperationException {
        vacationPackageManager.bookVacationPackage(vacationPackage, getCurrentUser());
    }

    @Override
    public List<VacationPackage> findBookedVacationPackagesOfCurrentUser() {
        return vacationPackageManager.findBookedVacationPackagesOfUser(getCurrentUser());
    }

    @Override
    public List<VacationPackage> findAvailableVacationPackages() {
        return vacationPackageManager.findAvailableVacationPackages(getCurrentUser());
    }

    @Override
    public VacationPackage findVacationPackageByName(String vacationPackageName) {
        return vacationPackageManager.findByName(vacationPackageName);
    }

    @Override
    public List<VacationPackage> filterVacationPackagesByConditions(FilterConditions filterConditions) {
        return vacationPackageManager.filterByConditions(filterConditions);
    }

    @Override
    public void register(User user) throws InvalidInputException {
        userManager.register(user);
    }
}
