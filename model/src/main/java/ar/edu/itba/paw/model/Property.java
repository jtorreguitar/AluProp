package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.exceptions.IllegalPropertyStateException;

import java.util.Collection;
import java.util.LinkedList;

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
    private Collection<Image> images;
    private long mainImageId;
    private Image mainImage;
    private long ownerId;
    private User owner;
    private Availability availability;

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

    public Collection<Image> getImages() {
        return images;
    }

    public long getMainImageId() {
        return mainImageId;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public User getOwner() {
        return owner;
    }

    public Availability getAvailability() {
        return availability;
    }


    public static class Builder {
        private Property property;

        public Builder() {
            this.property = new Property();
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
            this.property.images = property.images;
            this.property.services = property.services;
            this.property.ownerId = property.ownerId;
            this.property.owner = property.owner;
            this.property.availability = property.availability;
            return this;
        }

        public Property build() {
            checkStateLegality();
            initializeLists();
            return property;
        }

        private void checkStateLegality() {
            if(!captionIsValid()) throw new IllegalPropertyStateException("caption must be provided.");
            if(!descriptionIsValid()) throw new IllegalPropertyStateException("description must be provided.");
            if(!propertyTypeIsValid()) throw new IllegalPropertyStateException("property type must be provided.");
            if(!propertyNeighbourhoodIsValid()) throw new IllegalPropertyStateException("neighbourhood must be provided.");
            if(!capacityIsValid()) throw new IllegalPropertyStateException("capacity must be provided.");
            if(!priceIsValid()) throw new IllegalPropertyStateException("price must be provided.");
            if(!mainImageIsValid()) throw new IllegalPropertyStateException("image must be provided");
            if(!ownerIsValid()) throw new IllegalPropertyStateException("owner must be provided");
        }

        private boolean captionIsValid() {
            return !(property.caption == null || property.caption.length() < 1 || property.caption.length() > 700);
        }

        private boolean descriptionIsValid() {
            return !(property.description == null || property.description.length() < 1 || property.description.length() > 50);
        }

        private boolean propertyTypeIsValid() {
            return !(property.propertyType == null);
        }

        private boolean propertyNeighbourhoodIsValid() {
            return !(property.neighbourhoodId < 1 && property.neighbourhood == null);
        }

        private boolean capacityIsValid() {
            return !(property.capacity < 1 || property.capacity > 100);
        }

        private boolean priceIsValid() {
            return !(property.price < 1 || property.price > 9999999);
        }

        private boolean mainImageIsValid() {
            return !(property.mainImage == null && property.mainImageId < 1);
        }

        private boolean ownerIsValid() {
            return !(property.ownerId < 1 && property.owner == null);
        }

        private void initializeLists() {
            if(this.property.services == null) this.property.services = new LinkedList<>();
            if(this.property.images == null) this.property.images = new LinkedList<>();
            if(this.property.rules == null) this.property.rules = new LinkedList<>();
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

        public Builder withMainImage(Image mainImage) {
            this.property.mainImage = mainImage;
            return this;
        }

        public Builder withMainImageId(long mainImageId) {
            this.property.mainImageId = mainImageId;
            return this;
        }

        public Builder withImages(Collection<Image> images) {
            this.property.images = images;
            return this;
        }

        public Builder withOwnerId(long ownerId) {
            this.property.ownerId = ownerId;
            return this;
        }

        public Builder withOwner(User owner) {
            this.property.owner = owner;
            return this;
        }

        public Builder withAvailability(Availability availability){
            this.property.availability = availability;
            return this;
        }
    }
}
