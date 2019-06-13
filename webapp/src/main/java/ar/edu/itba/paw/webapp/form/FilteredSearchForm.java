package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.interfaces.enums.SearchablePrivacyLevel;
import ar.edu.itba.paw.interfaces.enums.SearchablePropertyType;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

public class FilteredSearchForm {

    public static final int NOT_APPLICABLE_PROPERTY_TYPE = -1;
    public static final int NOT_APPLICABLE_PRIVACY_LEVEL = -1;
    public static final int NOT_APPLICABLE_NEIGHBOURHOOD = -1;

    @Size(min=0, max=100)
    private String description;

    private int propertyType = NOT_APPLICABLE_PROPERTY_TYPE;

    private int neighbourhoodId = NOT_APPLICABLE_PRIVACY_LEVEL;

    private int privacyLevel = NOT_APPLICABLE_NEIGHBOURHOOD;

    @Range(min=0, max=100)
    private int capacity;

    @DecimalMin("0")
    @DecimalMax("9999999")
    private float minPrice;

    @DecimalMin("0")
    @DecimalMax("9999999")
    private float maxPrice;

    private long[] ruleIds;

    private long[] serviceIds;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public SearchablePropertyType getPropertyTypeAsEnum() {
        if(propertyType == NOT_APPLICABLE_PROPERTY_TYPE)
            return SearchablePropertyType.NOT_APPLICABLE;
        else
            return SearchablePropertyType.valueOf(propertyType);
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

    public SearchablePrivacyLevel getPrivacyLevelAsEnum() {
        if(privacyLevel == NOT_APPLICABLE_PRIVACY_LEVEL)
            return SearchablePrivacyLevel.NOT_APPLICABLE;
        else
            return SearchablePrivacyLevel.valueOf(privacyLevel);
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

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float price) {
        this.minPrice = price;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float price) {
        this.maxPrice = price;
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
}
