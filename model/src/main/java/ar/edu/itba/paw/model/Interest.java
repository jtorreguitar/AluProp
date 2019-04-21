package ar.edu.itba.paw.model;

public class Interest {
    private int id;
    private int propertyId;
    private int userId;

    public Interest(int id, int propertyId, int userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public int getUserId() {
        return userId;
    }
}
