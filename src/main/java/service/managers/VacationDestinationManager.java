package service.managers;

import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import model.VacationDestination;
import repository.VacationDestinationRepository;

import java.util.List;

public class VacationDestinationManager {
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();

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
    }

    public void delete(String name) throws InvalidOperationException {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(name);
        if (vacationDestination == null) {
            throw new InvalidOperationException("The vacation destination: " + name + " cannot be deleted, because it doesn't exist!");
        }
        vacationDestinationRepository.delete(vacationDestination.getId());
    }
}
