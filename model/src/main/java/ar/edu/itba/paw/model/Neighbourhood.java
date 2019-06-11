package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "neighbourhoods")
public class Neighbourhood {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "careers_id_seq")
    @SequenceGenerator(sequenceName = "careers_id_seq", name = "careers_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 75)
    private String name;

    @Transient
    private long cityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId")
    private City city;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighbourhoodId")
    private Collection<Property> properties;

    /* package */ Neighbourhood() { }

    public Neighbourhood(long id, String name, long cityId, Collection<Property> properties) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.properties = properties;
    }

    public Neighbourhood(long id, String name) {
        this.id = id;
        this.name = name;
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
