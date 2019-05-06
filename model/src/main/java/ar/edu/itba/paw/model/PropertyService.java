package ar.edu.itba.paw.model;

public class PropertyService {
    private long id;
    private long propertyId;
    private long ruleId;

    public PropertyService(long id, long propertyId, long ruleId) {
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
