package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.Availability;
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

    /* package */ static int PROPERTY_ID = 1;
    /* package */ static String CAPTION = "This house is very tested!!";
    /* package */ static int NEIGHBOURHOOD_ID = 1;
    /* package */ static int CAPACITY = 8;
    /* package */ static int PRICE = 10000;
    /* package */ static int IMAGE_ID = 1;
    /* package */ static int OWNER_ID = 1;


    @InjectMocks
    APPropertyService propertyService = new APPropertyService();

    @Mock
    PropertyDao propertyDao;

    @Mock
    ImageDao imageDao;

    @Mock
    NeighbourhoodDao neighbourhoodDao;

    @Mock
    UserDao userDao;

    @InjectMocks
    Property property;

    User user;

    @Before
    public void setUp(){
        property = Factories.propertyCreator();
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
        user = Factories.userCreator();

        Mockito.when(propertyDao.get(PROPERTY_ID))
                .thenReturn(property);

        int code = propertyService.showInterestOrReturnErrors(PROPERTY_ID, user);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, code);
    }

    @Test
    public void showInterestOrReturnErrorsWithInvalidPropertyDoesntTryToShowInterestTest(){
        user = Factories.userCreator();

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
        user = Factories.userCreator();

        Mockito.when(propertyDao.getInterestsOfUser(user.getId()))
                .thenReturn(expectedInterests);

        Collection<Property> realInterests = propertyService.getInterestsOfUser(user.getId());

        Assert.assertNotNull(realInterests);
        Assert.assertEquals(expectedInterests, realInterests);
    }
}
