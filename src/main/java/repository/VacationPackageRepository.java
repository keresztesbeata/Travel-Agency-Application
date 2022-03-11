package repository;

import model.PackageStatus;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.print.attribute.standard.Destination;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageRepository extends EntityRepository<VacationPackage, Long> {
    private static VacationPackageRepository instance;
    private static final String SQL_QUERY_FIND_PACKAGE_BY_STATUS = "select package from VacationPackage package where package.packageStatus = ?1";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_NAME = "select package from VacationPackage package where package.name = ?1";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_DESTINATION_NAME = "select package from VacationPackage package left join VacationDestination destination on package.vacationDestination = destination where destination.name = ?1";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_PRICE = "select package from VacationPackage package where package.price >= ?1 and package.price <= ?2";
    private static final String SQL_QUERY_FIND_PACKAGE_BY_PERIOD = "select package from VacationPackage package where package.startDate >= ?1 and package.endDate <= ?2";

    private VacationPackageRepository() {
        super(VacationPackage.class);
    }

    public static VacationPackageRepository getInstance() {
        if (instance == null) {
            instance = new VacationPackageRepository();
        }
        return instance;
    }

    public VacationPackage findByName(String name) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        VacationPackage vacationPackage = null;
        List<VacationPackage> vacationPackages = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_NAME)
                .setParameter(1, name)
                .getResultList();
        if (!vacationPackages.isEmpty()) {
            vacationPackage = vacationPackages.get(0);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return vacationPackage;
    }

    private Predicate equalDestination(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Destination destination) {
        return criteriaBuilder.equal(root.get("vacationDestination"), destination);
    }

    private Predicate equalName(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, String name) {
        return criteriaBuilder.equal(root.get("name"), name);
    }

    private Predicate inBetweenPeriod(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, LocalDate startDate, LocalDate endDate) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate),
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate));
    }

    private Predicate inBetweenPrice(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Double minPrice, Double maxPrice) {
        return criteriaBuilder.and(
                criteriaBuilder.gt(root.get("price"), minPrice),
                criteriaBuilder.lt(root.get("price"), maxPrice));
    }

    private Predicate hasStatus(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, PackageStatus packageStatus) {
        return criteriaBuilder.equal(root.get("packageStatus"), packageStatus);
    }

    public List<VacationPackage> findByPackageStatus(PackageStatus packageStatus) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<VacationPackage> vacationPackages = entityManager.createQuery(SQL_QUERY_FIND_PACKAGE_BY_STATUS)
                .setParameter(1, packageStatus)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByDestinationName(String vacationDestinationName) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<VacationPackage> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_DESTINATION_NAME)
                .setParameter(1, vacationDestinationName)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return destinations;
    }

    public List<VacationPackage> findByPrice(Double minPrice, Double maxPrice) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<VacationPackage> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_PRICE)
                .setParameter(1, minPrice)
                .setParameter(2, maxPrice)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return destinations;
    }

    public List<VacationPackage> findByPeriod(LocalDate startDate, LocalDate endDate) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        List<VacationPackage> destinations = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_PERIOD)
                .setParameter(1, startDate)
                .setParameter(2, endDate)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return destinations;
    }
}
