package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.*;

import java.sql.Date;
import java.util.HashSet;

import static ar.edu.itba.paw.service.APNotificationServiceTest.LINK;
import static ar.edu.itba.paw.service.APNotificationServiceTest.SUBJECT_CODE;
import static ar.edu.itba.paw.service.APNotificationServiceTest.TEXT_CODE;

public class Factories {

    static Proposal proposalCreator(){
        return new Proposal.Builder()
                .withCreator(userCreator())
                .withProperty(propertyCreator())
                .build();
    }

    static User userCreator(){
        User.Builder builder = new User.Builder();
        return builder.withEmail(APUserServiceTest.EMAIL)
                .withName(APUserServiceTest.NAME)
                .withLastName(APUserServiceTest.LAST_NAME)
                .withBirthDate(Date.valueOf("1996-07-12"))
                .withGender(Gender.MALE)
                .withPasswordHash(APUserServiceTest.PASSWORD)
                .withUniversityId(APUserServiceTest.UNIVERSITY_ID)
                .withCareerId(APUserServiceTest.CAREER_ID)
                .withBio(APUserServiceTest.BIO)
                .withContactNumber("1166765456")
                .withRole(Role.ROLE_GUEST)
                .build();
    }

    static User userCreatorWithID(int id){
        User.Builder builder = new User.Builder();
        return builder.withEmail(APUserServiceTest.EMAIL)
                .withId(id)
                .withName(APUserServiceTest.NAME)
                .withLastName(APUserServiceTest.LAST_NAME)
                .withBirthDate(Date.valueOf("1996-07-12"))
                .withGender(Gender.MALE)
                .withPasswordHash(APUserServiceTest.PASSWORD)
                .withUniversityId(APUserServiceTest.UNIVERSITY_ID)
                .withCareerId(APUserServiceTest.CAREER_ID)
                .withBio(APUserServiceTest.BIO)
                .withContactNumber("1166765456")
                .withRole(Role.ROLE_GUEST)
                .build();
    }

    static Property propertyCreator() {
        return new Property.Builder()
                .withId(APPropertyServiceTest.PROPERTY_ID)
                .withCaption(APPropertyServiceTest.CAPTION)
                .withDescription(APPropertyServiceTest.CAPTION)
                .withPropertyType(PropertyType.APARTMENT)
                .withNeighbourhood(new Neighbourhood(APPropertyServiceTest.NEIGHBOURHOOD_ID))
                .withPrivacyLevel(true)
                .withCapacity(APPropertyServiceTest.CAPACITY)
                .withPrice(APPropertyServiceTest.PRICE)
                .withRules(new HashSet<>())
                .withInterestedUsers(new HashSet<>())
                .withServices(new HashSet<>())
                .withImages(new HashSet<>())
                .withMainImage(new Image(APPropertyServiceTest.IMAGE_ID))
                .withOwnerId(APPropertyServiceTest.OWNER_ID)
                .withAvailability(Availability.valueOf("AVAILABLE"))
                .build();
    }

    static Notification notificationCreator(){
        return new Notification.Builder()
                .withUser(userCreator())
                .withSubjectCode(SUBJECT_CODE)
                .withTextCode(TEXT_CODE)
                .withLink(LINK)
                .withState(NotificationState.UNREAD)
                .build();
    }
}
