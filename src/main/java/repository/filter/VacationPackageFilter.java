package repository.filter;

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

    public void addNameFilterPredicate(String name) {
        addPredicate(containsName(name));
    }

    public void addDestinationFilterPredicate(VacationDestination destination) {
        addPredicate(equalDestination(destination));
    }

    public void addPriceFilterPredicate(Double minPrice, Double maxPrice) {
        addPredicate(inBetweenPrice(minPrice, maxPrice));
    }

    public void addStatusFilterPredicate(List<PackageStatus> packageStatuses) {
        addPredicate(hasStatus(packageStatuses));
    }

    public void addPeriodFilterPredicate(LocalDate startDate, LocalDate endDate) {
        if(startDate != null) {
            addPredicate(afterStartDate(startDate));
        }

        if(endDate != null) {
            addPredicate(beforeEndDate(endDate));
        }
    }

    private Predicate equalDestination(VacationDestination destination) {
        return criteriaBuilder.equal(root.get("vacationDestination"), destination);
    }

    private Predicate afterStartDate(LocalDate startDate) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    private Predicate beforeEndDate(LocalDate endDate) {
        return criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    private Predicate containsName(String name) {
        return criteriaBuilder.like(root.get("name"), "%" + name + "%");
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
