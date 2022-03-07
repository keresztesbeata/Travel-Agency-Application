package repository;

import model.VacationDestination;

import javax.persistence.EntityManager;
import java.util.List;

public class VacationDestinationRepository {
    private static VacationDestinationRepository instance;
    private static final String SQL_QUERY_FIND_DESTINATION_BY_NAME = "select destination from VacationDestination destination where destination.name = ?1";

    private VacationDestinationRepository() {
    }

    public static VacationDestinationRepository getInstance() {
        if (instance == null) {
            instance = new VacationDestinationRepository();
        }
        return instance;
    }

    public VacationDestination findVacationDestinationByName(String name) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        VacationDestination vacationDestination = null;
        List<VacationDestination> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_DESTINATION_BY_NAME)
                .setParameter(1, name)
                .getResultList();
        if (!destinations.isEmpty()) {
            vacationDestination = destinations.get(0);
        }
        entityManager.close();
        return vacationDestination;
    }

    public VacationDestination findVacationDestinationById(Long id) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        VacationDestination vacationDestination = entityManager.find(VacationDestination.class, id);
        entityManager.close();
        return vacationDestination;
    }

    public void saveVacationDestination(VacationDestination vacationDestination) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vacationDestination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteVacationDestination(Long id) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        VacationDestination vacationDestination = entityManager.find(VacationDestination.class, id);
        entityManager.remove(vacationDestination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}