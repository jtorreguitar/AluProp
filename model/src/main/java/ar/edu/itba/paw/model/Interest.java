package ar.edu.itba.paw.model;

public class Interest {
    private int id;
    private int propertyId;
    private int userId;

    public Interest(int id, int propertyId, int userId) {
        this.id = id;
        this.propertyId = propertyId;
        this.setUserId(userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
