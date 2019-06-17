package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.enums.SearchablePrivacyLevel;
import ar.edu.itba.paw.interfaces.enums.SearchablePropertyType;
import ar.edu.itba.paw.model.enums.PropertyOrder;

public class SearchableProperty {

    public static final long NOT_APPLICABLE_NEIGHBOURHOOD_ID = -1;

    private SearchablePropertyType propertyType;
    private SearchablePrivacyLevel privacyLevel;
    private long neighbourhoodId;
    private String description;
    private int capacity;
    private float minPrice;
    private float maxPrice;
    private long[] ruleIds;
    private long[] serviceIds;
    private PropertyOrder propertyOrder;

    private SearchableProperty() { }

    public SearchablePropertyType getPropertyType() {
        return propertyType;
    }

    public SearchablePrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public long getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public long[] getRuleIds() {
        return ruleIds;
    }

    public long[] getServiceIds() {
        return serviceIds;
    }

    public PropertyOrder getPropertyOrder() {
        return propertyOrder;
    }

    public static class Builder {

        private SearchableProperty searchableProperty;

        public Builder() {
            this.searchableProperty = new SearchableProperty();
        }

        public SearchableProperty build() {
            initializeAttributes();
            return searchableProperty;
        }

        private void initializeAttributes() {
            if(searchableProperty.propertyType == null)
                searchableProperty.propertyType = SearchablePropertyType.NOT_APPLICABLE;
            if(searchableProperty.privacyLevel == null)
                searchableProperty.privacyLevel = SearchablePrivacyLevel.NOT_APPLICABLE;
            if(searchableProperty.ruleIds == null)
                searchableProperty.ruleIds = new long[] { };
            if(searchableProperty.serviceIds == null)
                searchableProperty.serviceIds = new long[] { };
        }

        public Builder withPropertyType(SearchablePropertyType propertyType) {
            searchableProperty.propertyType = propertyType;
            return this;
        }

        public Builder withPrivacyLevel(SearchablePrivacyLevel privacyLevel) {
            searchableProperty.privacyLevel = privacyLevel;
            return this;
        }

        public Builder withNeighbourhoodId(long neighbourhoodId) {
            searchableProperty.neighbourhoodId = neighbourhoodId;
            return this;
        }

        public Builder withDescription(String description) {
            searchableProperty.description = description;
            return this;
        }

        public Builder withCapacity(int capacity) {
            searchableProperty.capacity = capacity;
            return this;
        }

        public Builder withMinPrice(float minPrice) {
            searchableProperty.minPrice = minPrice;
            return this;
        }

        public Builder withMaxPrice(float maxPrice) {
            searchableProperty.maxPrice = maxPrice;
            return this;
        }

        public Builder withRuleIds(long[] ruleIds) {
            searchableProperty.ruleIds = ruleIds;
            return this;
        }

        public Builder withServiceIds(long[] serviceIds) {
            searchableProperty.serviceIds = serviceIds;
            return this;
        }

        public Builder withPropertyOrder(PropertyOrder propertyOrder) {
            searchableProperty.propertyOrder = propertyOrder;
            return this;
        }
    }
}
