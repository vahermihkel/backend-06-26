package ee.mihkel.backend0626.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Boolean active;
    private String image;
    private Integer stock; // 0   /  null

    // @OneToMany ---> private List<Category> categories
    // @ManyToMany ---> private List<Category> categories
    // @OneToOne
    // @ManyToOne --> ühel kategoorial võib mitu toodet olla

    @ManyToOne
    private Category category;
}
