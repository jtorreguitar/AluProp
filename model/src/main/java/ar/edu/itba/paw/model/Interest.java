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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    /* package */ Interest() { }

    public Interest(Property property, User user) {
        this.property = property;
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }
}
