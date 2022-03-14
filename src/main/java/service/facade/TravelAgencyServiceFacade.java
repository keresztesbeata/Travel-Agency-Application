package service.facade;

import model.PackageStatus;
import model.VacationDestination;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.exceptions.PackageBookedException;
import service.facade.roles.TravelAgencyRole;
import service.managers.UserManager;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.stream.Collectors;

public class TravelAgencyServiceFacade extends UserService implements TravelAgencyRole {
    private VacationDestinationManager vacationDestinationManager = VacationDestinationManager.getInstance();
    private VacationPackageManager vacationPackageManager = VacationPackageManager.getInstance();
    private UserManager userManager = UserManager.getInstance();

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

    @Override
    public void registerListener(PropertyChangeListener listener) {
        vacationDestinationManager.addPropertyChangeListener(listener);
        vacationPackageManager.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        vacationPackageManager.removePropertyChangeListener(listener);
        vacationDestinationManager.removePropertyChangeListener(listener);
    }
}
