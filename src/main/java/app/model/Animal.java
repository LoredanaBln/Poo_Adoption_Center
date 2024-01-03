package app.model;

import lombok.Data;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Data
@NamedQueries(
        {@NamedQuery(name = "findAnimalById", query = "from Animal animal where animal.id = :id")}
)
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String species;

    @Column
    private Integer age;

    @Column
    private Boolean availability;
}
