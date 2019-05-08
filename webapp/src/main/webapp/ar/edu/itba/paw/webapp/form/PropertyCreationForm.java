package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.enums.PropertyType;

import javax.validation.constraints.Size;

public class PropertyCreationForm {
    @Size(min=1, max=100)
    private String caption;
    @Size(min=1, max=2000)
    private String description;
    private PropertyType propertyType;
    private long neighbourhoodId;
    private boolean privacyLevel;
    private int capacity;
    private float price;
    private long[] ruleIds;
    private long[] serviceIds;
    private long[] imageIds;
    private long mainImageId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public long getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public void setNeighbourhoodId(long neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public boolean getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(boolean privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long[] getRuleIds() {
        return ruleIds;
    }

    public void setRuleIds(long[] ruleIds) {
        this.ruleIds = ruleIds;
    }

    public long[] getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(long[] serviceIds) {
        this.serviceIds = serviceIds;
    }

    public long[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(long[] imageIds) {
        this.imageIds = imageIds;
    }

    public long getMainImageId() {
        return mainImageId;
    }

    public void setMainImageId(long mainImageId) {
        this.mainImageId = mainImageId;
    }
}
