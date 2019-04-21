package ar.edu.itba.paw.model;

import java.util.Collection;

public class Country {
    private long id;
    private String name;
    private Collection<Province> provinces;

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
}