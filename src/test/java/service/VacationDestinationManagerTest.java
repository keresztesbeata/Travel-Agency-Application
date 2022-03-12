package service;

import model.VacationDestination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.VacationDestinationManager;

class VacationDestinationManagerTest {

    @InjectMocks
    VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();

    private final String SAMPLE_DESTINATION_NAME = "Zagreb";
    private final String SAMPLE_DESTINATION_NAME_TO_DELETE = "Moscow";
    private final String INEXISTENT_DESTINATION_NAME = "Stari Kastel";

    @Order(value = 1)
    @Test
    void add() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertDoesNotThrow(() -> vacationDestinationManager.add(vacationDestination));

        VacationDestination duplicateVacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME);
        Assertions.assertThrows(InvalidInputException.class, () -> vacationDestinationManager.add(duplicateVacationDestination));
    }

    @Order(value = 2)
    @Test
    void delete() {
        VacationDestination vacationDestination = new VacationDestination(SAMPLE_DESTINATION_NAME_TO_DELETE);
        Assertions.assertDoesNotThrow(() -> vacationDestinationManager.add(vacationDestination));
        Assertions.assertDoesNotThrow(() -> vacationDestinationManager.delete(SAMPLE_DESTINATION_NAME_TO_DELETE));

        Assertions.assertThrows(InvalidOperationException.class, () -> vacationDestinationManager.delete(INEXISTENT_DESTINATION_NAME));
    }
}