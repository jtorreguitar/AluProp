package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.enums.PropertyType;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

public class PropertyCreationForm {
    @NotBlank
    @Size(max=100)
    private String caption;

    @NotBlank
    @Size(max=2000)
    private String description;

    @Range(min=0)
    private int propertyType;

    @Range(min=0)
    private int neighbourhoodId;

    @Range(min=0)
    private int privacyLevel;

    @Range(min=1, max=100)
    private int capacity;

    @Range
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
