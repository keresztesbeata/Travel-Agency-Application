package service;

import model.VacationDestination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;

class VacationDestinationServiceTest {

    @InjectMocks
    VacationDestinationService vacationDestinationService = new VacationDestinationService();

    private final String SAMPLE_DESTINATION_NAME = "Kastel Stari";
    private final String SAMPLE_DESTINATION_NAME_TO_DELETE = "Plitvitze";
    private final String INEXISTENT_DESTINATION_NAME = "Stari Kastel";

    @Order(value = 1)
    @Test
    void add() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));

        VacationDestination duplicateVacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertThrows(InvalidInputException.class, () -> vacationDestinationService.add(duplicateVacationDestination));
    }

    @Order(value = 2)
    @Test
    void delete() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME_TO_DELETE);
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.add(vacationDestination));
        Assertions.assertDoesNotThrow(() -> vacationDestinationService.delete(SAMPLE_DESTINATION_NAME_TO_DELETE));

        Assertions.assertThrows(InvalidOperationException.class, () -> vacationDestinationService.delete(INEXISTENT_DESTINATION_NAME));
    }
}