package service.facade;

import model.PackageStatus;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.UserManager;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;
import service.roles.TravelAgencyRole;

import java.util.List;

public class TravelAgencyServiceFacade implements TravelAgencyRole {
    private UserManager userManager = new UserManager();
    private VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();
    private VacationPackageManager vacationPackageManager = new VacationPackageManager();

    @Override
    public void login(User user) throws InvalidInputException {
        userManager.login(user);
    }

    @Override
    public User getCurrentUser() {
        return userManager.getCurrentUser();
    }

    @Override
    public void logout() {
        userManager.logout();
    }

    @Override
    public void addVacationDestination(VacationDestination vacationDestination) throws InvalidInputException {
        vacationDestinationManager.add(vacationDestination);
    }

    @Override
    public void deleteVacationDestination(String destinationName) throws InvalidOperationException {
        vacationPackageManager.deletePackagesOfDestination(destinationName);
        vacationDestinationManager.delete(destinationName);
    }

    @Override
    public List<VacationDestination> findAllDestinations() {
        return vacationDestinationManager.findAll();
    }

    @Override
    public void addVacationPackageForDestination(VacationPackage vacationPackage, String vacationDestinationName) throws InvalidInputException {
        VacationDestination vacationDestination = vacationDestinationManager.findByName(vacationDestinationName);
        if (vacationDestination == null) {
            throw new InvalidInputException("No vacation destination exists with the name: " + vacationDestinationName + "!");
        }
        vacationPackage.setVacationDestination(vacationDestination);
        vacationPackageManager.add(vacationPackage);
    }

    @Override
    public void editVacationPackage(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageManager.edit(vacationPackage);
    }

    @Override
    public void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException {
        vacationPackageManager.delete(vacationPackageName);
    }

    @Override
    public List<VacationPackage> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList) {
        return vacationPackageManager.filterByStatus(packageStatusList);
    }
}
