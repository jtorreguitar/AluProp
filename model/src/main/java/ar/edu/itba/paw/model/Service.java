package ar.edu.itba.paw.model;

import java.util.Collection;

public class Service {
    private long id;
    private String name;
    private Collection<Property> properties;

    public Service(long id, String name, Collection<Property> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    public Service(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Property> getProperties() {
        return properties;
    }
}
