package service;

import model.VacationDestination;
import repository.VacationDestinationRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

public class VacationDestinationService {
    private static VacationDestinationRepository destinationRepository = VacationDestinationRepository.getInstance();

    public void add(VacationDestination vacationDestination) throws InvalidInputException {
        String name = vacationDestination.getName();
        if (name == null) {
            throw new InvalidInputException("The name of the destination should not be empty!");
        }
        if (destinationRepository.findVacationDestinationByName(name) != null) {
            throw new InvalidInputException("The vacation destination: " + name + " has already been added!");
        }
        destinationRepository.saveVacationDestination(vacationDestination);
    }

    public void delete(String name) throws InvalidOperationException {
        VacationDestination vacationDestination = destinationRepository.findVacationDestinationByName(name);
        if (vacationDestination == null) {
            throw new InvalidOperationException("The vacation destination: " + name + " cannot be deleted, because it doesn't exist!");
        }
        destinationRepository.deleteVacationDestination(vacationDestination.getId());
    }
}
