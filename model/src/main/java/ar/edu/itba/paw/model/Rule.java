package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rules_id_seq")
    @SequenceGenerator(sequenceName = "rules_id_seq", name = "rules_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 250)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "propertyRules")
    private Collection<Property> properties;

    /* package */ Rule() { }

    public Rule(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Rule(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
