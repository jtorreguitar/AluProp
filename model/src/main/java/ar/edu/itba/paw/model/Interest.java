package ar.edu.itba.paw.model;

public class Interest {
    private long id;
    private long propertyId;
    private long userId;

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
