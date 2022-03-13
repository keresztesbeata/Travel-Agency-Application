package service.facade;

import model.PackageStatus;
import model.VacationDestination;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;
import service.roles.TravelAgencyRole;

import java.util.List;
import java.util.stream.Collectors;

public class TravelAgencyServiceFacade extends UserService implements TravelAgencyRole {
    private VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();
    private VacationPackageManager vacationPackageManager = new VacationPackageManager();

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
    public void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException {
        vacationPackageManager.delete(vacationPackageName);
    }

    @Override
    public List<VacationPackageDTO> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList) {
        return vacationPackageManager.filterByStatus(packageStatusList);
    }
}
