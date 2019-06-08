package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "universities")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "universities_id_seq")
    @SequenceGenerator(sequenceName = "universities_id_seq", name = "universities_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 75)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<User> users;

    /* package */ University() { }

    public University(long id, String name, Collection<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public University(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
