package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "propertyServices")
public class PropertyService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "propertyServices_id_seq")
    @SequenceGenerator(sequenceName = "propertyServices_id_seq", name = "propertyServices_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceId")
    private Service service;

    /* package */ PropertyService() { }

    public PropertyService(Property property, Service service) {
        this.property = property;
        this.service = service;
    }

    public long getId() {
        return id;
    }
}
