package repository;

import model.PackageStatus;
import model.VacationDestination;
import model.VacationPackage;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class VacationPackageRepository extends EntityRepository<VacationPackage, Long> {
    private static VacationPackageRepository instance;
    private static final String SQL_QUERY_FIND_PACKAGE_BY_NAME = "select package from VacationPackage package where package.name = ?1";

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

    public List<VacationPackage> findByPackageStatus(List<PackageStatus> packageStatuses) {
        EntityManager entityManager = getEntityManager();
        VacationPackageFilter vacationPackageFilter = new VacationPackageFilter(entityManager);
        List<VacationPackage> vacationPackages = vacationPackageFilter.filterByStatus(packageStatuses);
        entityManager.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByDestination(VacationDestination destination) {
        EntityManager entityManager = getEntityManager();
        VacationPackageFilter vacationPackageFilter = new VacationPackageFilter(entityManager);
        List<VacationPackage> vacationPackages = vacationPackageFilter.filterByDestination(destination);
        entityManager.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByPrice(Double minPrice, Double maxPrice) {
        EntityManager entityManager = getEntityManager();
        VacationPackageFilter vacationPackageFilter = new VacationPackageFilter(entityManager);
        List<VacationPackage> vacationPackages = vacationPackageFilter.filterByPrice(minPrice, maxPrice);
        entityManager.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByPeriod(LocalDate startDate, LocalDate endDate) {
        EntityManager entityManager = getEntityManager();
        VacationPackageFilter vacationPackageFilter = new VacationPackageFilter(entityManager);
        List<VacationPackage> vacationPackages = vacationPackageFilter.filterByPeriod(startDate, endDate);
        entityManager.close();
        return vacationPackages;
    }

}
