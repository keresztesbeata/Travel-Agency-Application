package service;

import model.VacationDestination;
import model.VacationPackage;
import repository.VacationDestinationRepository;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.validators.VacationPackageValidator;

import java.util.List;

public class VacationPackageService {
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();

    public void add(VacationPackage vacationPackage) throws InvalidInputException {
        VacationPackageValidator vacationPackageValidator = new VacationPackageValidator(vacationPackage);
        vacationPackageValidator.validate();

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
        vacationPackageRepository.deleteById(vacationPackage.getId());
    }

    public VacationPackage findByName(String name) {
        return vacationPackageRepository.findByName(name);
    }

    public List<VacationPackage> findByDestination(String name) {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(name);
        return vacationPackageRepository.findByDestination(vacationDestination);
    }

    public List<VacationPackage> findByPrice(Double minPrice, Double maxPrice) {
        return vacationPackageRepository.findByPrice(minPrice, maxPrice);
    }

    public void edit(VacationPackage vacationPackage) throws InvalidOperationException {
        //todo
    }
}
