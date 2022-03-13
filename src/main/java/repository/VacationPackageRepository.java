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
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> criteriaQuery = criteriaBuilder.createQuery(VacationPackage.class);
        Root<VacationPackage> root = criteriaQuery.from(VacationPackage.class);
        Predicate condition = criteriaBuilder.like(root.get("name"), name);
        criteriaQuery.select(root).where(condition);
        List<VacationPackage> result = entityManager.createQuery(criteriaQuery)
                .getResultList();
        VacationPackage vacationPackage = null;
        if (!result.isEmpty()) {
            vacationPackage = result.get(0);
        }
        entityManager.close();
        return vacationPackage;
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

    public List<VacationPackage> filterByConditions(FilterConditions filterConditions) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VacationPackage> criteriaQuery = criteriaBuilder.createQuery(VacationPackage.class);
        Root<VacationPackage> root = criteriaQuery.from(VacationPackage.class);
        Predicate condition = createFilterPredicate(criteriaBuilder, root, filterConditions);
        criteriaQuery.select(root).where(condition);
        List<VacationPackage> result = entityManager.createQuery(criteriaQuery)
                .getResultList();
        entityManager.close();
        return result;
    }

    private Predicate createFilterPredicate(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, FilterConditions filterConditions) {
        Predicate condition = criteriaBuilder.and();
        if (filterConditions.getStartDate().isPresent()) {
            condition = addAfterStartDateCondition(criteriaBuilder, root, condition, filterConditions.getStartDate().get());
        }
        if (filterConditions.getEndDate().isPresent()) {
            condition = addBeforeEndDateCondition(criteriaBuilder, root, condition, filterConditions.getEndDate().get());
        }
        if (filterConditions.getKeyword().isPresent()) {
            condition = addContainsKeywordCondition(criteriaBuilder, root, condition, filterConditions.getKeyword().get());
        }
        if (filterConditions.getDestinationName().isPresent()) {
            condition = addHasDestinationCondition(criteriaBuilder, root, condition, filterConditions.getVacationDestination());
        }
        if (filterConditions.getMinPrice().isPresent()) {
            condition = addMinPriceCondition(criteriaBuilder, root, condition, filterConditions.getMinPrice().get());
        }
        if (filterConditions.getMaxPrice().isPresent()) {
            condition = addMaxPriceCondition(criteriaBuilder, root, condition, filterConditions.getMaxPrice().get());
        }
        if (filterConditions.isAvailable()) {
            condition = addAvailabilityCondition(criteriaBuilder, root, condition);
        }
        return condition;
    }

    public Predicate addMinPriceCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, Double minPrice) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.gt(root.get("price"), minPrice));
    }

    public Predicate addMaxPriceCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, Double maxPrice) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.lt(root.get("price"), maxPrice));
    }

    public Predicate addHasDestinationCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, VacationDestination vacationDestination) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.equal(root.get("vacationDestination"), vacationDestination));
    }

    private Predicate addContainsKeywordCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, String keyword) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
    }

    private Predicate addAfterStartDateCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, LocalDate startDate) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.greaterThanOrEqualTo(root.get("from"), startDate));
    }

    private Predicate addBeforeEndDateCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition, LocalDate endDate) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.lessThanOrEqualTo(root.get("to"), endDate));
    }

    private Predicate addAvailabilityCondition(CriteriaBuilder criteriaBuilder, Root<VacationPackage> root, Predicate condition) {
        return criteriaBuilder.and(condition,
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("packageStatus"), PackageStatus.NOT_BOOKED),
                        criteriaBuilder.equal(root.get("packageStatus"), PackageStatus.IN_PROGRESS)));
    }

}
