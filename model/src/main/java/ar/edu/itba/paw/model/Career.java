package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "careers_id_seq")
    @SequenceGenerator(sequenceName = "careers_id_seq", name = "careers_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "careerId")
    private Collection<User> users;

    /* package */ Career() { }

    public Career(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Career(long id, String name, Collection<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
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
