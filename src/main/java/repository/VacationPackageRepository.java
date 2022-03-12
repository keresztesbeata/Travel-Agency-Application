package repository;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageRepository extends EntityRepository<VacationPackage, Long> {
    private static VacationPackageRepository instance;

    private static final String SQL_QUERY_FIND_PACKAGE_BY_NAME = "select package from VacationPackage package left join fetch package.vacationDestination where package.name = ?1";
    private static final String SQL_QUERY_FILTER_BY_PRICE = "select package from VacationPackage package where package.price >= ?1 and package.price <= ?2";
    private static final String SQL_QUERY_FILTER_BY_PERIOD = "select package from VacationPackage package where package.startDate >= ?1 and package.endDate <= ?2";
    private static final String SQL_QUERY_FILTER_BY_START_DATE = "select package from VacationPackage package where package.startDate >= ?1";
    private static final String SQL_QUERY_FILTER_BY_END_DATE = "select package from VacationPackage package where package.endDate <= ?1";
    private static final String SQL_DELETE_FROM_JOINED_TABLE_BY_ID = "delete from user_vacation_package where vacation_package_id = :id";

    private VacationPackageRepository() {
        super(VacationPackage.class);
    }

    public static VacationPackageRepository getInstance() {
        if (instance == null) {
            instance = new VacationPackageRepository();
        }
        return instance;
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(SQL_DELETE_FROM_JOINED_TABLE_BY_ID)
                .setParameter("id", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public VacationPackage findByName(String name) {
        EntityManager entityManager = getEntityManager();
        VacationPackage vacationPackage = null;
        List<VacationPackage> vacationPackages = entityManager
                .createQuery(SQL_QUERY_FIND_PACKAGE_BY_NAME)
                .setParameter(1, name)
                .getResultList();
        if (!vacationPackages.isEmpty()) {
            vacationPackage = vacationPackages.get(0);
        }
        return vacationPackage;
    }

    public List<VacationPackage> filterByPrice(Double minPrice, Double maxPrice) {
        EntityManager entityManager = getEntityManager();
        return (List<VacationPackage>) entityManager
                .createQuery(SQL_QUERY_FILTER_BY_PRICE)
                .setParameter(1, minPrice)
                .setParameter(2, maxPrice)
                .getResultList();
    }

    public List<VacationPackage> filterByPeriod(LocalDate startDate, LocalDate endDate) {
        EntityManager entityManager = getEntityManager();
        return (List<VacationPackage>) entityManager
                .createQuery(SQL_QUERY_FILTER_BY_PERIOD)
                .setParameter(1, startDate)
                .setParameter(2, endDate)
                .getResultList();
    }

    public List<VacationPackage> filterByStartDate(LocalDate startDate) {
        EntityManager entityManager = getEntityManager();
        return (List<VacationPackage>) entityManager
                .createQuery(SQL_QUERY_FILTER_BY_START_DATE)
                .setParameter(1, startDate)
                .getResultList();
    }

    public List<VacationPackage> filterByEndDate(LocalDate endDate) {
        EntityManager entityManager = getEntityManager();
        return (List<VacationPackage>) entityManager
                .createQuery(SQL_QUERY_FILTER_BY_END_DATE)
                .setParameter(1, endDate)
                .getResultList();
    }


    public List<VacationPackage> filterByStatus(List<PackageStatus> packageStatuses) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> criteriaQuery = criteriaBuilder.createQuery(VacationPackage.class);
        Root<VacationPackage> root = criteriaQuery.from(VacationPackage.class);
        if (!packageStatuses.isEmpty()) {
            Predicate condition = criteriaBuilder.or(packageStatuses.stream()
                    .map(packageStatus -> criteriaBuilder.equal(root.get("packageStatus"), packageStatus))
                    .toArray(Predicate[]::new));
            criteriaQuery.select(root).where(condition);
        } else {
            criteriaQuery.select(root);
        }
        List<VacationPackage> result = entityManager.createQuery(criteriaQuery)
                .getResultList();
        entityManager.close();
        return result;
    }

    public List<VacationPackage> filterByKeyword(String keyword) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> criteriaQuery = criteriaBuilder.createQuery(VacationPackage.class);
        Root<VacationPackage> root = criteriaQuery.from(VacationPackage.class);
        Predicate condition = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
        criteriaQuery.select(root).where(condition);
        List<VacationPackage> result = entityManager.createQuery(criteriaQuery)
                .getResultList();
        entityManager.close();
        return result;
    }

    public List<VacationPackage> filterByDestination(VacationDestination vacationDestination) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> criteriaQuery = criteriaBuilder.createQuery(VacationPackage.class);
        Root<VacationPackage> root = criteriaQuery.from(VacationPackage.class);
        Predicate condition = criteriaBuilder.equal(root.get("vacationDestination"), vacationDestination);
        criteriaQuery.select(root).where(condition);
        List<VacationPackage> result = entityManager.createQuery(criteriaQuery)
                .getResultList();
        entityManager.close();
        return result;
    }
}
