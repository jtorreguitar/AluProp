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

    @Transient
    private long propertyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @Transient
    private long serviceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceId")
    private Service service;

    /* package */ PropertyService() { }

    public PropertyService(long id, long propertyId, long serviceId) {
        this.id = id;
        this.propertyId = propertyId;
        this.serviceId = serviceId;
    }

    public long getId() {
        return id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public long getServiceId() {
        return serviceId;
    }
}
