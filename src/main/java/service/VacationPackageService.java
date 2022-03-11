package service;

import model.PackageStatus;
import model.VacationPackage;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.validators.InputValidator;
import service.validators.VacationPackageValidator;

import java.time.LocalDate;
import java.util.List;

public class VacationPackageService {
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
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

    public void delete(String name) throws InvalidOperationException {
        VacationPackage vacationPackage = vacationPackageRepository.findByName(name);

        if (vacationPackage == null) {
            throw new InvalidOperationException("The vacation package: " + name + " cannot be deleted, because it doesn't exist!");
        }
        vacationPackageRepository.delete(vacationPackage);
    }

    public List<VacationPackage> findAll() {
        return vacationPackageRepository.findAll();
    }

    public List<VacationPackage> findByPackageStatus(PackageStatus packageStatus) {
        return vacationPackageRepository.findByPackageStatus(packageStatus);
    }

    public VacationPackage findByName(String name) {
        return vacationPackageRepository.findByName(name);
    }

    public List<VacationPackage> findByDestinationName(String destinationName) {
        return vacationPackageRepository.findByDestinationName(destinationName);
    }

    public List<VacationPackage> findByPrice(Double minPrice, Double maxPrice) {
        return vacationPackageRepository.findByPrice(minPrice, maxPrice);
    }

    public List<VacationPackage> findByPeriod(LocalDate startDate, LocalDate endDate) {
        return vacationPackageRepository.findByPeriod(startDate, endDate);
    }

    public void edit(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackage);

        vacationPackageRepository.update(vacationPackage);
    }
}
