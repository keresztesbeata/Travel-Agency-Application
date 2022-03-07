package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
