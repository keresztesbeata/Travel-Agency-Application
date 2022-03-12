package repository;

import lombok.Getter;
import lombok.Setter;
import model.PackageStatus;
import model.VacationDestination;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class VacationPackageFilterModel {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;
    private Set<PackageStatus> packageStatusSet;
    private Optional<VacationDestination> vacationDestination;
    private String vacationDestinationName;

    private VacationPackageFilterModel(String name, Double minPrice, Double maxPrice, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Set<PackageStatus> packageStatusSet, Optional<VacationDestination> vacationDestination) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.packageStatusSet = packageStatusSet;
        this.vacationDestination = vacationDestination;
    }

    public static class VacationPackageFilterBuilder {
        private String name = "";
        private Double minPrice = 0.0;
        private Double maxPrice = Double.MAX_VALUE;
        private Optional<LocalDate> startDate = Optional.empty();
        private Optional<LocalDate> endDate = Optional.empty();
        private Set<PackageStatus> packageStatusSet = new HashSet<>();
        private Optional<VacationDestination> vacationDestination = Optional.empty();

        public VacationPackageFilterBuilder withNameFilter(String name) {
            this.name = (name != null && !name.isEmpty()) ? name : "";
            return this;
        }

        public VacationPackageFilterBuilder withPriceFilter(Double minPrice, Double maxPrice) {
            this.minPrice = (minPrice == null) ? 0.0 : minPrice;
            this.maxPrice = (maxPrice == null) ? Double.MAX_VALUE : maxPrice;
            return this;
        }

        public VacationPackageFilterBuilder withPeriodFilter(Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }

        public VacationPackageFilterBuilder withDestinationFilter(Optional<VacationDestination> vacationDestination) {
            this.vacationDestination = vacationDestination;
            return this;
        }

        public VacationPackageFilterBuilder withPackageStatusFilter(List<PackageStatus> packageStatusList) {
            this.packageStatusSet.addAll(packageStatusList);
            return this;
        }

        public VacationPackageFilterModel build() {
            return new VacationPackageFilterModel(name, minPrice, maxPrice, startDate, endDate, packageStatusSet, vacationDestination);
        }

    }
}
