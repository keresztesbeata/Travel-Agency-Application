package service.roles;

import service.exceptions.InvalidInputException;
import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import service.exceptions.InvalidOperationException;

import java.util.List;

public interface TravelAgencyRole extends UserRole{

    void addVacationDestination(VacationDestination vacationDestination) throws InvalidInputException;

    void deleteVacationDestination(String destinationName) throws InvalidOperationException;

    List<VacationDestination> findAllDestinations();

    void addVacationPackageForDestination(VacationPackage vacationPackage, String vacationDestinationName) throws InvalidInputException;

    void editVacationPackage(VacationPackage vacationPackage) throws InvalidInputException;

    void deleteVacationPackage(String vacationPackageName) throws InvalidOperationException;

    List<VacationPackage> filterVacationPackagesByStatus(List<PackageStatus> packageStatusList);

}
