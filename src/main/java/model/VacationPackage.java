package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacation_package")
@Getter
@Setter
@NoArgsConstructor
public class VacationPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vacationDestinationId", nullable = false)
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
    private Integer maxNrOfBookings;

    @Column
    private Integer nrOfBookings;

    @Column
    private String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageStatus packageStatus;

    @ManyToMany(mappedBy = "vacationPackages")
    private Set<User> users = new HashSet<>();

    private VacationPackage(String name, Double price, LocalDate startDate, LocalDate endDate, Integer maxNrOfBookings, String details, VacationDestination vacationDestination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNrOfBookings = maxNrOfBookings;
        this.nrOfBookings = 0;
        this.details = details;
        this.vacationDestination = vacationDestination;
        this.packageStatus = PackageStatus.NOT_BOOKED;
    }

    public void incrementNrOfBookings() {
        nrOfBookings++;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public static class VacationPackageBuilder {
        private VacationDestination vacationDestination;
        private String name;
        private Double price;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer maxCapacity;
        private String details;

        public VacationPackageBuilder withMaxCapacity(Integer maxCapacity) {
            this.maxCapacity = maxCapacity;
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
            return new VacationPackage(name, price, startDate, endDate, maxCapacity, details, vacationDestination);
        }
    }

    @Override
    public String toString() {
        return "VacationPackage{" +
                "id=" + id +
                ", vacationDestination=" + vacationDestination +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxNrOfBookings=" + maxNrOfBookings +
                ", nrOfBookings=" + nrOfBookings +
                ", details='" + details + '\'' +
                ", packageStatus=" + packageStatus +
                '}';
    }
}
