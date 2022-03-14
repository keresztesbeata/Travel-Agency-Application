package service.managers;

import model.VacationDestination;
import model.VacationPackage;
import repository.FilterConditions;
import repository.VacationDestinationRepository;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

import java.util.List;

public class VacationDestinationManager extends AbstractManager {
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
    private static final Integer OLD_VALUE = 1;
    private static final Integer NEW_VALUE = 2;
    private static VacationDestinationManager instance;

    private VacationDestinationManager() {}

    public static VacationDestinationManager getInstance() {
        if(instance == null) {
            instance = new VacationDestinationManager();
        }
        return instance;
    }

    public VacationDestination findByName(String name) {
        if (name != null) {
            return vacationDestinationRepository.findByName(name);
        }
        return null;
    }

    public List<VacationDestination> findAll() {
        return vacationDestinationRepository.findAll();
    }

    public void add(String vacationDestinationName) throws InvalidInputException {
        if (vacationDestinationName == null) {
            throw new InvalidInputException("The name of the destination should not be empty!");
        }
        if (vacationDestinationRepository.findByName(vacationDestinationName) != null) {
            throw new InvalidInputException("The vacation destination: " + vacationDestinationName + " has already been added!");
        }
        VacationDestination vacationDestination = new VacationDestination();
        vacationDestination.setName(vacationDestinationName);
        vacationDestinationRepository.save(vacationDestination);
        support.firePropertyChange(PROPERTY_CHANGE.ADDED_ENTRY.name(), OLD_VALUE, NEW_VALUE);
    }

    public void delete(String name) throws InvalidOperationException {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(name);
        if (vacationDestination == null) {
            throw new InvalidOperationException("The vacation destination: " + name + " cannot be deleted,\nbecause it doesn't exist!");
        }
        FilterConditions filterConditions = new FilterConditions.FilterConditionsBuilder()
                .withDestinationName(name)
                .build();
        List<VacationPackage> vacationPackages = vacationPackageRepository.filterByConditions(filterConditions);
        vacationPackages.forEach(vacationPackage ->
                vacationPackageRepository.delete(vacationPackage.getId()));
        vacationDestinationRepository.delete(vacationDestination.getId());
        support.firePropertyChange(PROPERTY_CHANGE.DELETED_ENTRY.name(), OLD_VALUE, NEW_VALUE);
    }
}
