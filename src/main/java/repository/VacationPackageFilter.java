package repository;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageFilter extends QueryFilter<VacationPackage> {
    public VacationPackageFilter(EntityManager entityManager) {
        super(entityManager, VacationPackage.class);
    }

    public List<VacationPackage> filterByDestination(VacationDestination destination) {
        QueryFilter<VacationPackage> queryFilter = new QueryFilter<>(entityManager, VacationPackage.class);
        queryFilter.addPredicate(equalDestination(destination));
        return queryFilter.applyFilter();
    }

    public List<VacationPackage> filterByPeriod(LocalDate startDate, LocalDate endDate) {
        QueryFilter<VacationPackage> queryFilter = new QueryFilter<>(entityManager, VacationPackage.class);
        queryFilter.addPredicate(inBetweenPeriod(startDate, endDate));
        return queryFilter.applyFilter();
    }

    public List<VacationPackage> filterByPrice(Double minPrice, Double maxPrice) {
        QueryFilter<VacationPackage> queryFilter = new QueryFilter<>(entityManager, VacationPackage.class);
        queryFilter.addPredicate(inBetweenPrice(minPrice, maxPrice));
        return queryFilter.applyFilter();
    }

    public List<VacationPackage> filterByStatus(List<PackageStatus> packageStatuses) {
        QueryFilter<VacationPackage> queryFilter = new QueryFilter<>(entityManager, VacationPackage.class);
        packageStatuses.forEach(packageStatus -> queryFilter.addPredicate(hasStatus(packageStatus)));
        return queryFilter.applyFilter();
    }

    private Predicate equalDestination(VacationDestination destination) {
        return criteriaBuilder.equal(root.get("vacationDestination"), destination);
    }

    private Predicate inBetweenPeriod(LocalDate startDate, LocalDate endDate) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate),
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate));
    }

    private Predicate inBetweenPrice(double minPrice, double maxPrice) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice),
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    private Predicate hasStatus(PackageStatus packageStatus) {
        return criteriaBuilder.equal(root.get("packageStatus"), packageStatus);
    }

}
