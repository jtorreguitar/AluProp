package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "propertyRules")
public class PropertyRule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "propertyRules_id_seq")
    @SequenceGenerator(sequenceName = "propertyRules_id_seq", name = "propertyRules_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    /* package */ PropertyRule() { }

    public long getId() {
        return id;
    }

    public Property getProperty() {
        return property;
    }

    public Rule getRule() {
        return rule;
    }
}
