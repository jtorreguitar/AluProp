package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.PropertyType;

import java.util.Collection;

public class Property {
    private long id;
    private String caption;
    private String description;
    private String image;
    private PropertyType propertyType;
    private long neighbourhoodId;
    private Neighbourhood neighbourhood;
    private boolean privacyLevel;
    private int capacity;
    private float price;
    private Collection<Rule> rules;
    private Collection<User> interestedUsers;

    private Property() { }

    public long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public long getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public boolean isPrivacyLevel() {
        return privacyLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getPrice() {
        return price;
    }

    public Collection<Rule> getRules() {
        return rules;
    }

    public Collection<User> getInterestedUsers() {
        return interestedUsers;
    }

    public static class Builder {
        public static final String MUST_BE_PROVIDED = "must be provided.";
        private Property property;

        public Builder() {
            this.property = new Property();
        }

        public Property build() {
            if(property.id < 1) throw new IllegalArgumentException("id" + MUST_BE_PROVIDED);
            if(property.caption == null && property.caption == "") throw new IllegalArgumentException("caption" + MUST_BE_PROVIDED);
            if(property.description == null && property.description == "") throw new IllegalArgumentException("description" + MUST_BE_PROVIDED);
            if(property.propertyType == null) throw new IllegalArgumentException("property type" + MUST_BE_PROVIDED);
            if(property.neighbourhoodId < 1 && property.neighbourhood == null) throw new IllegalArgumentException("neighbourhood" + MUST_BE_PROVIDED);
            if(property.capacity < 1) throw new IllegalArgumentException("capacity" + MUST_BE_PROVIDED);
            if(property.price <= 0) throw new IllegalArgumentException("price" + MUST_BE_PROVIDED);
            return property;
        }

        public Builder withId(long id) {
            property.id = id;
            return this;
        }

        public Builder withCaption(String caption) {
            property.caption = caption;
            return this;
        }

        public Builder withDescription(String Description) {
            property.description = Description;
            return this;
        }

        public Builder withPropertyType(PropertyType propertyType) {
            property.propertyType = propertyType;
            return this;
        }

        public Builder withNeighbourhoodId(long neighbourhoodId) {
            property.neighbourhoodId = neighbourhoodId;
            return this;
        }

        public Builder withNeighbourhood(Neighbourhood neighbourhood) {
            property.neighbourhood = neighbourhood;
            return this;
        }

        public Builder withPrivacyLevel(boolean privacyLevel) {
            property.privacyLevel = privacyLevel;
            return this;
        }

        public Builder withCapacity(int capacity) {
            property.capacity = capacity;
            return this;
        }

        public Builder withPrice(float price) {
            property.price = price;
            return this;
        }

        public Builder withRules(Collection<Rule> rules) {
            property.rules = rules;
            return this;
        }

        public Builder withInterestedUsers(Collection<User> interestedUsers) {
            property.interestedUsers = interestedUsers;
            return this;
        }


    }
}