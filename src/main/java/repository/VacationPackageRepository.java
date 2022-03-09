package repository;

import model.VacationDestination;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.Destination;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageRepository {
    private static VacationPackageRepository instance;
    private static final String SQL_QUERY_FIND_PACKAGE_BY_NAME = "select package from VacationPackage package where package.name = ?1";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_DESTINATION = "select package from VacationPackage package left join VacationDestination destination on package.destination_id = destination.id where package.destination = ?1";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_PRICE = "select package from VacationPackage package where package.price >= ?1 and package.price <= ?2";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_PERIOD = "select package from VacationPackage package where date(package.startDate) >= '?1' and package.endDate <= '?2'";

    private VacationPackageRepository() {
    }

    public static VacationPackageRepository getInstance() {
        if (instance == null) {
            instance = new VacationPackageRepository();
        }
        return instance;
    }

    public VacationPackage findById(Long id) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        VacationPackage vacationPackage = entityManager.find(VacationPackage.class, id);
        entityManager.close();
        return vacationPackage;
    }

    public VacationPackage findByName(String name) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        VacationPackage vacationPackage = null;
        List<VacationPackage> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_NAME)
                .setParameter(1, name)
                .getResultList();
        if (!destinations.isEmpty()) {
            vacationPackage = destinations.get(0);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return vacationPackage;
    }

    public List<VacationPackage> findByDestination(VacationDestination vacationDestination) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        List<VacationPackage> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_DESTINATION)
                .setParameter(1, vacationDestination)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return destinations;
    }

    public List<VacationPackage> findByPrice(Double minPrice, Double maxPrice) {
        return null;
    }

    public List<VacationPackage> findByPeriod(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public void save(VacationPackage vacationPackage) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vacationPackage);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteById(Long id) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        VacationPackage vacationPackage = entityManager.find(VacationPackage.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(vacationPackage);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
