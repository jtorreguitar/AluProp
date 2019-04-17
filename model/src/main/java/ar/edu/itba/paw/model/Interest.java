package ar.edu.itba.paw.model;

public class Interest {
    private int id;
    private int propertyId;
    private String description;
    private String email;

    public Interest(int id, int propertyId, String description, String email) {
        this.id = id;
        this.propertyId = propertyId;
        this.description = description;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
