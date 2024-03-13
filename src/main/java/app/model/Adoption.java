    package app.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import lombok.Data;
    import lombok.ToString;

    import javax.persistence.*;
    import java.util.Date;

    @Entity
    @Table
    @Data
    @NamedQueries({
            @NamedQuery(name = "findAdoptionById", query = "from Adoption adoption where adoption.id =: id")
    })
    public class Adoption {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
        @ToString.Exclude
        @JsonIgnore
        private Adopter adopter;

        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
        @ToString.Exclude
        @JsonIgnore
        private Staff staff;

        @ManyToOne(cascade = CascadeType.ALL)
        private Animal animal;

        @Column
        private Date date;
    }
