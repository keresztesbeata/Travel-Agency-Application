package service.facade;

import model.User;
import model.VacationPackage;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.UserManager;
import service.managers.VacationPackageManager;
import service.roles.RegularUserRole;

import java.time.LocalDate;
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
    public List<VacationPackage> filterVacationPackagesByDestination(String destination) {
        return vacationPackageManager.filterVacationPackagesByDestination(destination);
    }

    @Override
    public List<VacationPackage> filterVacationPackagesByPeriod(LocalDate startDate, LocalDate endDate) {
        return vacationPackageManager.filterVacationPackagesByPeriod(startDate, endDate);
    }

    @Override
    public List<VacationPackage> filterVacationPackagesByPrice(Double minPrice, Double maxPrice) {
        return vacationPackageManager.filterVacationPackagesByPrice(minPrice, maxPrice);
    }

    @Override
    public List<VacationPackage> filterVacationPackagesByKeyword(String keyword) {
        return vacationPackageManager.filterVacationPackagesByKeyword(keyword);
    }

    @Override
    public void register(User user) throws InvalidInputException {
        userManager.register(user);
    }
}
