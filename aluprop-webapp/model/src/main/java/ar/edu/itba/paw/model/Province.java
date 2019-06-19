package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provinces_id_seq")
    @SequenceGenerator(sequenceName = "provinces_id_seq", name = "provinces_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryId")
    private Country country;

    @Column(length = 75)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId")
    private Collection<City> cities;

    /* package */ Province() { }

    public long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Collection<City> getCities() {
        return cities;
    }
}
