package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vacation_destination")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class VacationDestination {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VacationPackage> packages;

    public VacationDestination(String name) {
        this.name = name;
    }
}
