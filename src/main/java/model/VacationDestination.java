package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacation_destination")
@Getter
@Setter
@NoArgsConstructor
public class VacationDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "vacationDestination")
    private Set<VacationPackage> vacationPackages = new HashSet<>();

    public VacationDestination(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VacationDestination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
