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

    @Transient
    private long countryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryId")
    private Country country;

    @Column(length = 75)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId")
    private Collection<City> cities;

    /* package */ Province() { }

    public Province(long id, long countryId, String name, Collection<City> cities) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
        this.cities = cities;
    }

    public Province(long id, Country country, String name, Collection<City> cities) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.cities = cities;
    }

    public Province(long id, long countryId, String name) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
    }

    public Province(long id, Country country, String name) {
        this.id = id;
        this.country = country;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getCountryId() {
        return countryId;
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
