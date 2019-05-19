package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

public class PropertyCreationForm {
    @Size(min=1, max=700)
    private String caption;

    @Size(min=1, max=50)
    private String description;

    @Range(min=0)
    private int propertyType;

    @Range(min=0)
    private int neighbourhoodId;

    @Range(min=0)
    private int privacyLevel;

    @Range(min=1, max=100)
    private int capacity;

    @DecimalMin("1")
    @DecimalMax("9999999")
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

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public int getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public void setNeighbourhoodId(int neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public int getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(int privacyLevel) {
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
