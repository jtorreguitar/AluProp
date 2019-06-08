package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "services_id_seq")
    @SequenceGenerator(sequenceName = "services_id_seq", name = "services_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "propertyServices")
    private Collection<Property> properties;

    /* package */ Service() { }

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
