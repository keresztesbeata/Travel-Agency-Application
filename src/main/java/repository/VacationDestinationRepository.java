package repository;

import model.VacationDestination;

import javax.persistence.EntityManager;
import java.util.List;

public class VacationDestinationRepository extends EntityRepository<VacationDestination, Long>{
    private static VacationDestinationRepository instance;
    private static final String SQL_QUERY_FIND_DESTINATION_BY_NAME = "select destination from VacationDestination destination where destination.name = ?1";

    private VacationDestinationRepository() {
        super(VacationDestination.class);
    }

    public static VacationDestinationRepository getInstance() {
        if (instance == null) {
            instance = new VacationDestinationRepository();
        }
        return instance;
    }

    public VacationDestination findByName(String name) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        VacationDestination vacationDestination = null;
        List<VacationDestination> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_DESTINATION_BY_NAME)
                .setParameter(1, name)
                .getResultList();

        if (!destinations.isEmpty()) {
            vacationDestination = destinations.get(0);
        }
        entityManager.getTransaction().commit();
        entityManager.close();

        return vacationDestination;
    }

}
