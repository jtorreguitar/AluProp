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
    private Collection<Province> provinces;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<City> cities;

    /* package */ Country() { }

    public Country(long id, String name, Collection<Province> provinces) {
        this.id = id;
        this.name = name;
        this.provinces = provinces;
    }

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
