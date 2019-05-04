package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.PropertyType;

import java.io.InputStream;
import java.util.Collection;

public class Property {
    private long id;
    private String caption;
    private String description;
    private PropertyType propertyType;
    private long neighbourhoodId;
    private Neighbourhood neighbourhood;
    private boolean privacyLevel;
    private int capacity;
    private float price;
    private Collection<Rule> rules;
    private Collection<User> interestedUsers;
    private Collection<Service> services;
    private long mainImageId;
    private InputStream mainImage;

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

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public long getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public boolean getPrivacyLevel() {
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

    public Collection<Service> getServices() {
        return services;
    }

    public InputStream getMainImage() {
        return mainImage;
    }

    public long getMainImageId() { return mainImageId; }

    public static class Builder {
        public static final String MUST_BE_PROVIDED = "must be provided.";
        private Property property;

        public Builder() {
            this.property = new Property();
        }

        public Property build() {
            if(property.id < 1) throw new IllegalStateException("id" + MUST_BE_PROVIDED);
            if(property.caption == null || property.caption.equals("")) throw new IllegalStateException("caption" + MUST_BE_PROVIDED);
            if(property.description == null || property.description.equals("")) throw new IllegalStateException("description" + MUST_BE_PROVIDED);
            if(property.propertyType == null) throw new IllegalStateException("property type" + MUST_BE_PROVIDED);
            if(property.neighbourhoodId < 1 && property.neighbourhood == null) throw new IllegalStateException("neighbourhood" + MUST_BE_PROVIDED);
            if(property.capacity < 1) throw new IllegalStateException("capacity" + MUST_BE_PROVIDED);
            if(property.price <= 0) throw new IllegalStateException("price" + MUST_BE_PROVIDED);
            if(property.mainImage == null && property.mainImageId < 0) throw new IllegalStateException("image must be provided")
            return property;
        }

        public Builder fromProperty(Property property) {
            this.property.id = property.id;
            this.property.caption = property.caption;
            this.property.description = property.description;
            this.property.propertyType = property.propertyType;
            this.property.neighbourhoodId = property.neighbourhoodId;
            this.property.neighbourhood = property.neighbourhood;
            this.property.privacyLevel = property.privacyLevel;
            this.property.capacity = property.capacity;
            this.property.price = property.price;
            this.property.rules = property.rules;
            this.property.interestedUsers = property.interestedUsers;
            this.property.mainImageId = property.mainImageId;
            this.property.mainImage = property.mainImage;
            return this;
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

        public Builder withServices(Collection<Service> services) {
            this.property.services = services;
            return this;
        }

        public Builder withMainImage(InputStream mainImage) {
            this.property.mainImage = mainImage;
            return this;
        }

        public Builder withMainImageId(long mainImageId) {
            this.property.mainImageId = mainImageId;
            return this;
        }
    }
}
