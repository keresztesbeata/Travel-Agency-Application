package service.facade.roles;

import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import model.PackageStatus;
import model.VacationDestination;
import service.exceptions.InvalidOperationException;
import service.exceptions.PackageBookedException;

import java.util.List;

public interface TravelAgencyRole extends UserRole{

    void addVacationDestination(String vacationDestinationName) throws InvalidInputException;

    void deleteVacationDestination(String vacationDestinationName) throws InvalidOperationException;

    List<String> findAllDestinations();

    VacationDestination findDestinationByName(String destinationName);

    void addVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException;

    void editVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException;

    void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException, PackageBookedException;

    void unSafeDeleteVacationPackage(String vacationPackageName) throws InvalidOperationException;

    List<VacationPackageDTO> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList);

}
