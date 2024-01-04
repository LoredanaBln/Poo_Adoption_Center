package app.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "my_address")
@Data
@NamedQueries(
        {@NamedQuery(name = "findAddressById", query = "from Address address where address.id =: id")}
)
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String street;

    @Column
    private Integer number;

    @Column
    private String city;
}
