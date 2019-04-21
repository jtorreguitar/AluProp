package ar.edu.itba.paw.model;

import java.util.Collection;

public class Neighbourhood {
    private long id;
    private String name;
    private long cityId;
    private Collection<Property> properties;

    public Neighbourhood(long id, String name, long cityId, Collection<Property> properties) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.properties = properties;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCityId() {
        return cityId;
    }

    public Collection<Property> getProperties() {
        return properties;
    }
}