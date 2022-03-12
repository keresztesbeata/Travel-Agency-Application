package repository;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;
import repository.filter.VacationPackageFilter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageRepository extends EntityRepository<VacationPackage, Long> {
    private static VacationPackageRepository instance;
    private static final String SQL_QUERY_FIND_PACKAGE_BY_NAME = "select package from VacationPackage package where package.name = ?1";
    private VacationPackageFilter vacationPackageFilter;

    private VacationPackageRepository() {
        super(VacationPackage.class);
        EntityManager entityManager = getEntityManager();
        vacationPackageFilter = new VacationPackageFilter(entityManager);
    }

    public static VacationPackageRepository getInstance() {
        if (instance == null) {
            instance = new VacationPackageRepository();
        }
        return instance;
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
        entityManager.close();

        return vacationPackage;
    }

    @Override
    public List<VacationPackage> findAll() {
        List<VacationPackage> vacationPackages = super.findAll();
        vacationPackageFilter.resetFilter();
        return vacationPackages;
    }

    public void withNameFilter(String name) {
        vacationPackageFilter.addNameFilterPredicate(name);
    }

    public void withPackageStatusFilter(List<PackageStatus> packageStatuses) {
        vacationPackageFilter.addStatusFilterPredicate(packageStatuses);
    }

    public void withDestinationFilter(VacationDestination destination) {
        vacationPackageFilter.addDestinationFilterPredicate(destination);
    }

    public void withPriceFilter(Double minPrice, Double maxPrice) {
        vacationPackageFilter.addPriceFilterPredicate(minPrice, maxPrice);
    }

    public void withPeriodFilter(LocalDate startDate, LocalDate endDate) {
        vacationPackageFilter.addPeriodFilterPredicate(startDate, endDate);
    }

    public List<VacationPackage> filter() {
        return vacationPackageFilter.applyFilter();
    }

}
