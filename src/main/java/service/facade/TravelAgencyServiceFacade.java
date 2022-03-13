package service.facade;

import model.PackageStatus;
import model.VacationDestination;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.exceptions.PackageBookedException;
import service.managers.UserManager;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;
import service.roles.TravelAgencyRole;

import java.util.List;
import java.util.stream.Collectors;

public class TravelAgencyServiceFacade extends UserService implements TravelAgencyRole {
    private VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();
    private VacationPackageManager vacationPackageManager = new VacationPackageManager();
    private UserManager userManager = new UserManager();

    @Override
    public void addVacationDestination(String vacationDestinationName) throws InvalidInputException {
        vacationDestinationManager.add(vacationDestinationName);
    }

    @Override
    public VacationDestination findDestinationByName(String destinationName) {
        return vacationDestinationManager.findByName(destinationName);
    }

    @Override
    public void deleteVacationDestination(String vacationDestinationName) throws InvalidOperationException {
        vacationPackageManager.deletePackagesOfDestination(vacationDestinationName);
        vacationDestinationManager.delete(vacationDestinationName);
    }

    @Override
    public List<String> findAllDestinations() {
        return vacationDestinationManager.findAll()
                .stream()
                .map(VacationDestination::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void addVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        vacationPackageManager.add(vacationPackageDTO);
    }

    @Override
    public void editVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        vacationPackageManager.edit(vacationPackageDTO);
    }

    @Override
    public void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException, PackageBookedException {
        vacationPackageManager.safeDelete(vacationPackageName);
    }

    @Override
    public void unSafeDeleteVacationPackage(String vacationPackageName) throws InvalidOperationException {
        vacationPackageManager.unSafeDelete(vacationPackageName);
    }

    @Override
    public List<VacationPackageDTO> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList) {
        return vacationPackageManager.filterByStatus(packageStatusList);
    }
}
