package model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    private LocalDate from;

    @Column(nullable = false)
    private LocalDate to;

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

    public VacationPackage(String name, Double price, LocalDate from, LocalDate to, Integer maxNrOfBookings, String details, VacationDestination vacationDestination) {
        this.name = name;
        this.price = price;
        this.from = from;
        this.to = to;
        this.maxNrOfBookings = maxNrOfBookings;
        this.nrOfBookings = 0;
        this.details = details;
        this.vacationDestination = vacationDestination;
        this.packageStatus = PackageStatus.NOT_BOOKED;
    }

    public VacationPackage() {
        this.maxNrOfBookings = 0;
        this.nrOfBookings = 0;
        this.packageStatus = PackageStatus.NOT_BOOKED;
    }

    public void incrementNrOfBookings() {
        nrOfBookings++;
    }

    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public String toString() {
        return "VacationPackage{" +
                "id=" + id +
                ", vacationDestination=" + vacationDestination +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", from=" + from +
                ", to=" + to +
                ", maxNrOfBookings=" + maxNrOfBookings +
                ", nrOfBookings=" + nrOfBookings +
                ", details='" + details + '\'' +
                ", packageStatus=" + packageStatus +
                '}';
    }
}
