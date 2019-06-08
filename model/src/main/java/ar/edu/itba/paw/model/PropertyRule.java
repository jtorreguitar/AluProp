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

    @Transient
    private long propertyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @Transient
    private long ruleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    /* package */ PropertyRule() { }

    public PropertyRule(long id, long propertyId, long ruleId) {
        this.id = id;
        this.propertyId = propertyId;
        this.ruleId = ruleId;
    }

    public long getId() {
        return id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public long getRuleId() {
        return ruleId;
    }
}
