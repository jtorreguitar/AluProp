package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.enums.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.HttpURLConnection;
import java.sql.Date;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class APPropertyServiceTest {

    private static int PROPERTY_ID = 1;
    private static String CAPTION = "This house is very tested!!";
    private static int NEIGHBOURHOOD_ID = 1;
    private static int CAPACITY = 8;
    private static int PRICE = 10000;
    private static int IMAGE_ID = 1;
    private static int OWNER_ID = 1;


    @InjectMocks
    APPropertyService propertyService = new APPropertyService();

    @Mock
    PropertyDao propertyDao;

    @Mock
    ImageDao imageDao;

    @Mock
    RuleDao ruleDao;

    @Mock
    ServiceDao serviceDao;

    @Mock
    NeighbourhoodDao neighbourhoodDao;

    @Mock
    UserDao userDao;

    @InjectMocks
    Property property;

    User user;

    private Property createDummyProperty() {
        Property.Builder builder = new Property.Builder();
        return builder.withId(PROPERTY_ID)
                .withCaption(CAPTION)
                .withDescription(CAPTION)
                .withPropertyType(PropertyType.APARTMENT)
                .withNeighbourhoodId(NEIGHBOURHOOD_ID)
                .withPrivacyLevel(true)
                .withCapacity(CAPACITY)
                .withPrice(PRICE)
                .withRules(new HashSet<>())
                .withInterestedUsers(new HashSet<>())
                .withServices(new HashSet<>())
                .withImages(new HashSet<>())
                .withMainImageId(IMAGE_ID)
                .withOwnerId(OWNER_ID)
                .withAvailability(true)
                .build();
    }

    private User createDummyUser(){
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


    @Before
    public void setUp(){
        property = createDummyProperty();
    }



    @Test
    public void getPropertyTest() {
        Mockito.when(propertyDao.get(PROPERTY_ID))
                .thenReturn(property);

        Property maybeProperty = propertyService.get(PROPERTY_ID);

        Assert.assertNotNull(maybeProperty);
        Assert.assertEquals(property, maybeProperty);
    }

    @Test
    public void showInterestOrReturnErrorsWithoutErrorsTest() {
        user = createDummyUser();

        Mockito.when(propertyDao.showInterest(PROPERTY_ID, user))
                .thenReturn(true);

        Mockito.when(propertyDao.get(PROPERTY_ID))
                .thenReturn(property);

        int code = propertyService.showInterestOrReturnErrors(PROPERTY_ID, user);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, code);
    }

    @Test
    public void showInterestOrReturnErrorsWithValidPropertyAndUserButShowInterestFailsTest(){
        user = createDummyUser();

        Mockito.when(propertyDao.showInterest(PROPERTY_ID, user))
                .thenReturn(false);

        Mockito.when(propertyDao.get(PROPERTY_ID))
                .thenReturn(property);

        int code = propertyService.showInterestOrReturnErrors(PROPERTY_ID, user);
        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, code);
    }

    @Test
    public void showInterestOrReturnErrorsWithInvalidPropertyDoesntTryToShowInterestTest(){
        user = createDummyUser();

        Mockito.when(propertyDao.get(PROPERTY_ID))
                .thenReturn(null);

        int code = propertyService.showInterestOrReturnErrors(PROPERTY_ID, user);
        Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, code);

    }

    @Test
    public void getPropertyWithRelatedEntitiesTest() {
        Mockito.when(propertyDao.getPropertyWithRelatedEntities(PROPERTY_ID))
                .thenReturn(property);

        Property maybeProperty = propertyService.getPropertyWithRelatedEntities(PROPERTY_ID);

        Assert.assertNotNull(maybeProperty);
        Assert.assertEquals(property, maybeProperty);
    }

    @Test
    public void createValidProperty() {
        Mockito.when(propertyDao.create(Mockito.eq(property)))
                .thenReturn(property);

        Mockito.when(imageDao.get(IMAGE_ID))
                .thenReturn(new Image(IMAGE_ID));

        Mockito.when(neighbourhoodDao.get(NEIGHBOURHOOD_ID))
                .thenReturn(new Neighbourhood(1, "Test friendly"));

        Either<Property, Collection<String>> maybeProperty = propertyService.create(property);

        Assert.assertNotNull(maybeProperty);
        Assert.assertTrue(maybeProperty.hasValue());
        Assert.assertEquals(property, maybeProperty.value());
    }

    @Test
    public void createInvalidPropertyImageAndNeighbourhoodNotInDBTest(){
        Either<Property, Collection<String>> maybeProperty = propertyService.create(property);

        Assert.assertNotNull(maybeProperty);
        Assert.assertFalse(maybeProperty.hasValue());
        Assert.assertEquals(2, maybeProperty.alternative().size());
        Assert.assertTrue(maybeProperty.alternative().contains(APPropertyService.IMAGE_NOT_EXISTS));
        Assert.assertTrue(maybeProperty.alternative().contains(APPropertyService.NEIGHBOURHOOD_NOT_EXISTS));

    }

    @Test
    public void createInvalidPropertyImageNotInDatabase(){
        Mockito.when(neighbourhoodDao.get(NEIGHBOURHOOD_ID))
                .thenReturn(new Neighbourhood(1, "Test friendly"));

        Either<Property, Collection<String>> maybeProperty = propertyService.create(property);

        Assert.assertNotNull(maybeProperty);
        Assert.assertFalse(maybeProperty.hasValue());
        Assert.assertEquals(1, maybeProperty.alternative().size());
        Assert.assertTrue(maybeProperty.alternative().contains(APPropertyService.IMAGE_NOT_EXISTS));

    }

    @Test
    public void createInvalidPropertyNeighbourhoodNotInDatabase(){

        Mockito.when(imageDao.get(IMAGE_ID))
                .thenReturn(new Image(IMAGE_ID));


        Either<Property, Collection<String>> maybeProperty = propertyService.create(property);

        Assert.assertNotNull(maybeProperty);
        Assert.assertFalse(maybeProperty.hasValue());
        Assert.assertEquals(1, maybeProperty.alternative().size());
        Assert.assertTrue(maybeProperty.alternative().contains(APPropertyService.NEIGHBOURHOOD_NOT_EXISTS));
    }
    @Test
    public void getInterestsOfUser() {
        Collection<Property> expectedInterests = new HashSet<>();
        expectedInterests.add(property);
        user = createDummyUser();

        Mockito.when(propertyDao.getInterestsOfUser(user.getId()))
                .thenReturn(expectedInterests);

        Collection<Property> realInterests = propertyService.getInterestsOfUser(user.getId());

        Assert.assertNotNull(realInterests);
        Assert.assertEquals(expectedInterests, realInterests);
    }
}
