package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.print.attribute.standard.Destination;
import java.time.LocalDate;

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
    @JoinColumn(name="vacation_destination_id")
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
    private Long nrOfPeople;

    @Column
    private String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageStatus packageStatus;

    private VacationPackage(String name, Double price, LocalDate startDate, LocalDate endDate, Long nrOfPeople, String details, VacationDestination vacationDestination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nrOfPeople = nrOfPeople;
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
        private Long nrOfPeople;
        private String details;

        public VacationPackageBuilder withNrOfPeople(Long nrOfPeople) {
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
