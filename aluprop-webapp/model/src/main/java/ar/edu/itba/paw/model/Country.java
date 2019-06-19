package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_id_seq")
    @SequenceGenerator(sequenceName = "countries_id_seq", name = "countries_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 75)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Collection<Province> provinces;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Collection<City> cities;

    /* package */ Country() { }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Province> getProvinces() {
        return provinces;
    }

    public Collection<City> getCities() {
        return cities;
    }
}
