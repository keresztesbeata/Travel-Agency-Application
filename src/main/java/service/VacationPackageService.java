package service;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import repository.VacationDestinationRepository;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.validators.InputValidator;
import service.validators.VacationPackageValidator;

import java.time.LocalDate;
import java.util.List;

public class VacationPackageService {
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();
    private InputValidator<VacationPackage> vacationPackageValidator;

    public VacationPackageService() {
        vacationPackageValidator = new VacationPackageValidator();
    }

    public void add(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackage);

        if (vacationPackageRepository.findByName(vacationPackage.getName()) != null) {
            throw new InvalidInputException("Duplicate name! Another vacation package: " + vacationPackage.getName() + " with the same name has already been added!");
        }
        vacationPackageRepository.save(vacationPackage);
    }

    public void edit(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackage);

        if (vacationPackageRepository.findByName(vacationPackage.getName()) != null) {
            throw new InvalidInputException("Duplicate name! Another vacation package: " + vacationPackage.getName() + " with the same name has already been added!");
        }
        vacationPackageRepository.update(vacationPackage);
    }

    public void delete(String name) throws InvalidOperationException {
        VacationPackage vacationPackage = vacationPackageRepository.findByName(name);

        if (vacationPackage == null) {
            throw new InvalidOperationException("The vacation package: " + name + " cannot be deleted, because it doesn't exist!");
        }
        vacationPackageRepository.delete(vacationPackage.getId());
    }

    public List<VacationPackage> findAll() {
        return vacationPackageRepository.findAll();
    }

    public VacationPackage findByName(String name) {
        return vacationPackageRepository.findByName(name);
    }

    public List<VacationPackage> filter() {
        return vacationPackageRepository.filter();
    }

    public void withPackageStatusFilter(List<PackageStatus> packageStatuses) {
        vacationPackageRepository.withPackageStatusFilter(packageStatuses);
    }

    public void withContainsNameFilter(String nameContained) {
        vacationPackageRepository.withNameFilter(nameContained);
    }

    public void withDestinationFilter(String destinationName) {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(destinationName);
        vacationPackageRepository.withDestinationFilter(vacationDestination);
    }

    public void withPriceFilter(Double minPrice, Double maxPrice) {
        if(minPrice == null || minPrice < 0) {
            minPrice = 0d;
        }
        if(maxPrice == null || maxPrice < 0) {
            maxPrice = Double.MAX_VALUE;
        }
        vacationPackageRepository.withPriceFilter(minPrice, maxPrice);
    }

    public void preparePeriodFilter(LocalDate startDate, LocalDate endDate) {
        vacationPackageRepository.withPeriodFilter(startDate, endDate);
    }

}
