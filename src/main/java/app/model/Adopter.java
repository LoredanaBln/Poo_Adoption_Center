package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries(
        {@NamedQuery(name = "findUserByName", query = "from Adopter pers where pers.name = :name"),
                @NamedQuery(name = "findUserByNameAndPassword", query = "from Adopter pers where pers.name = :name and pers.password=:password"),
                @NamedQuery(name = "findUserById", query = "from Adopter pers where pers.id = :id"),
                @NamedQuery(name = "findAllUsers", query = "from Adopter")
        }
)
public class Adopter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Adoption> adoptions;



}
