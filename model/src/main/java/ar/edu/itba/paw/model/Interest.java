package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interests_id_seq")
    @SequenceGenerator(sequenceName = "interests_id_seq", name = "interests_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Transient
    private long propertyId;

    @Transient
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    /* package */ Interest() { }

    public Interest(long id, long propertyId, long userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public long getUserId() {
        return userId;
    }
}
