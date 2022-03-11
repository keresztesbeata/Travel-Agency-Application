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
        addPredicate(equalDestination(destination));
        return applyFilter();
    }

    public List<VacationPackage> filterByPeriod(LocalDate startDate, LocalDate endDate) {
        addPredicate(inBetweenPeriod(startDate, endDate));
        return applyFilter();
    }

    public List<VacationPackage> filterByPrice(Double minPrice, Double maxPrice) {
        addPredicate(inBetweenPrice(minPrice, maxPrice));
        return applyFilter();
    }

    public List<VacationPackage> filterByStatus(List<PackageStatus> packageStatuses) {
        addPredicate(hasStatus(packageStatuses));
        return applyFilter();
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

    private Predicate hasStatus(List<PackageStatus> packageStatuses) {
        return criteriaBuilder.or(packageStatuses.stream()
                .map(packageStatus -> criteriaBuilder.equal(root.get("packageStatus"), packageStatus))
                .toArray(Predicate[]::new));
    }

}
