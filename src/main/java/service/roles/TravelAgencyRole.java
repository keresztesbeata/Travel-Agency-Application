package service.roles;

import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import model.PackageStatus;
import model.VacationDestination;
import service.exceptions.InvalidOperationException;

import java.util.List;

public interface TravelAgencyRole extends UserRole{

    void addVacationDestination(VacationDestination vacationDestination) throws InvalidInputException;

    void deleteVacationDestination(String destinationName) throws InvalidOperationException;

    List<String> findAllDestinations();

    void addVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException;

    void editVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidInputException;

    void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException;

    List<VacationPackageDTO> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList);

}
