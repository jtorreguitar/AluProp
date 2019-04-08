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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the propertyId
     */
    public int getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}