package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.exceptions.IllegalPropertyStateException;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "properties")
public class Property {

    public static final boolean INDIVIDUAL_PRIVACY_LEVEL = false;
    public static final boolean SHARED_PRIVACY_LEVEL = true;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "properties_id_seq")
    @SequenceGenerator(sequenceName = "properties_id_seq", name = "properties_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 700)
    private String caption;

    @Column(length = 50)
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "neighbourhoodId")
    private Neighbourhood neighbourhood;

    @Column
    private boolean privacyLevel;

    @Column
    private int capacity;

    @Column
    private float price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "propertyRules",
                joinColumns = @JoinColumn(name = "propertyId"),
                inverseJoinColumns = @JoinColumn(name = "ruleId"))
    private Collection<Rule> rules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "interests",
                joinColumns = @JoinColumn(name = "propertyId"),
                inverseJoinColumns = @JoinColumn(name = "userId"))
    private Collection<User> interestedUsers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "propertyServices",
                joinColumns = @JoinColumn(name = "propertyId"),
                inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private Collection<Service> services;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId")
    private Collection<Image> images;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mainImageId")
    private Image mainImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private User owner;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId")
    private Collection<Proposal> proposals;

    /* package */ Property() { }

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

    public Image getMainImage() {
        return mainImage;
    }

    public User getOwner() {
        return owner;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Collection<Proposal> getProposals() {
        return proposals;
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
            this.property.neighbourhood = property.neighbourhood;
            this.property.privacyLevel = property.privacyLevel;
            this.property.capacity = property.capacity;
            this.property.price = property.price;
            this.property.rules = property.rules;
            this.property.interestedUsers = property.interestedUsers;
            this.property.mainImage = property.mainImage;
            this.property.images = property.images;
            this.property.services = property.services;
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
            return property.neighbourhood != null;
        }

        private boolean capacityIsValid() {
            return !(property.capacity < 1 || property.capacity > 100);
        }

        private boolean priceIsValid() {
            return !(property.price < 1 || property.price > 9999999);
        }

        private boolean mainImageIsValid() {
            return property.mainImage != null;
        }

        private boolean ownerIsValid() {
            return property.owner != null;
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

        public Builder withImages(Collection<Image> images) {
            this.property.images = images;
            return this;
        }

        public Builder withOwner(User owner) {
            this.property.owner = owner;
            return this;
        }

        public Builder withOwnerId(long id) {
            property.owner = new User(id);
            return this;
        }

        public Builder withAvailability(Availability availability){
            this.property.availability = availability;
            return this;
        }
    }
}
