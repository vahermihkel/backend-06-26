package ee.mihkel.backend0626.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    //@Column(unique = true)
    private String email;
    private String password;
    private String personCode;
    private PersonRole role;

    @OneToOne(cascade = CascadeType.ALL) // saan lisada koos Personiga uut Aadressi. kustub automaatselt
    private Address address;
}
