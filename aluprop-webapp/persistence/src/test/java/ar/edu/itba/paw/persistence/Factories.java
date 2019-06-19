package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Neighbourhood;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.enums.Role;

import java.sql.Date;
import java.util.HashSet;

public class Factories {
    public static User userCreator(){
        return new User.Builder()
                .withEmail(APUserDaoTest.EMAIL2)
                .withName(APUserDaoTest.NAME2)
                .withLastName("Tester")
                .withBirthDate(Date.valueOf("1996-07-12"))
                .withGender(Gender.MALE)
                .withPasswordHash("SuperS3CReTP4ssW0rd")
                .withUniversityId(1)
                .withCareerId(1)
                .withBio("Hello, how are you today?")
                .withContactNumber("1166765456")
                .withRole(Role.ROLE_GUEST)
                .build();
    }

    public static  Property propertyCreator() {
        return new Property.Builder()
                .withId(APPropertyDaoTest.PROPERTY_ID)
                .withCaption(APPropertyDaoTest.CAPTION)
                .withDescription(APPropertyDaoTest.CAPTION)
                .withPropertyType(PropertyType.APARTMENT)
                .withNeighbourhood(new Neighbourhood(APPropertyDaoTest.NEIGHBOURHOOD_ID))
                .withPrivacyLevel(true)
                .withCapacity(APPropertyDaoTest.CAPACITY)
                .withPrice(APPropertyDaoTest.PRICE)
                .withRules(new HashSet<>())
                .withInterestedUsers(new HashSet<>())
                .withServices(new HashSet<>())
                .withImages(new HashSet<>())
                .withMainImage(new Image(APPropertyDaoTest.IMAGE_ID))
                .withOwnerId(APPropertyDaoTest.OWNER_ID)
                .withAvailability(Availability.valueOf("AVAILABLE"))
                .build();
    }
}
