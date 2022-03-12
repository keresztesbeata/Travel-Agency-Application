package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "vacation_package")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class VacationPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="vacationDestinationId", nullable = false)
    private VacationDestination vacationDestination;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(precision = 2)
    private Double price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer nrOfPeople;

    @Column
    private Integer nrOfBookings;

    @Column
    private String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageStatus packageStatus;

    @ManyToMany(mappedBy = "vacationPackage")
    private Set<User> users;

    private VacationPackage(String name, Double price, LocalDate startDate, LocalDate endDate, Integer nrOfPeople, String details, VacationDestination vacationDestination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nrOfPeople = nrOfPeople;
        this.nrOfBookings = 0;
        this.details = details;
        this.vacationDestination = vacationDestination;
        this.packageStatus = PackageStatus.NOT_BOOKED;
    }

    public static class VacationPackageBuilder {
        private VacationDestination vacationDestination;
        private String name;
        private Double price;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer nrOfPeople;
        private String details;

        public VacationPackageBuilder withNrOfPeople(Integer nrOfPeople) {
            this.nrOfPeople = nrOfPeople;
            return this;
        }

        public VacationPackageBuilder withDestination(VacationDestination vacationDestination) {
            this.vacationDestination = vacationDestination;
            return this;
        }

        public VacationPackageBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public VacationPackageBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public VacationPackageBuilder withPeriod(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }

        public VacationPackageBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public VacationPackage build() {
            return new VacationPackage(name, price, startDate, endDate, nrOfPeople, details, vacationDestination);
        }
    }
}
