package ar.edu.itba.paw.model;

import java.util.Collection;

public class Province {
    private long id;
    private long countryId;
    private Country country;
    private String name;
    private Collection<City> cities;

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