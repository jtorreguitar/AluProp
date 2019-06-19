package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Availability;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//@RunWith(MockitoJUnitRunner.class)

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APPropertyDaoTest {

    /* package */ final static String CAPTION2 = "el mejor depto";
    /* package */ final static String DESCRIPTION = "posta que el mejor depto";

    /* package */ static int PROPERTY_ID = 1;
    /* package */ static String CAPTION = "This house is very tested!!";
    /* package */ static int NEIGHBOURHOOD_ID = 1;
    /* package */ static int CAPACITY = 8;
    /* package */ static int PRICE = 10000;
    /* package */ static int IMAGE_ID = 1;
    /* package */ static int OWNER_ID = 1;



    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PropertyDao propertyDao;



    @Test
    public void getPropertyTest(){
        int propertyID = 1;
        Property maybeProperty;

        maybeProperty = propertyDao.get(propertyID);
        Assert.assertNotNull(maybeProperty);
        Assert.assertEquals(propertyID, maybeProperty.getId());
        Assert.assertEquals(CAPTION2, maybeProperty.getCaption());
        Assert.assertEquals(DESCRIPTION, maybeProperty.getDescription());
    }

    @Test
    public void getAllActivePropertiesTest(){
        Long expectedRowCount = entityManager
                                .createQuery("SELECT COUNT(p.id) FROM Property p WHERE p.availability = 'AVAILABLE'", Long.class)
                                .getSingleResult();
        int realRowCount = propertyDao.getAllActive(new PageRequest(0,0)).size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }

    @Test
    public void getAllActiveOnlyContainsActivePropertiesTest(){
        PageRequest pageRequest = new PageRequest(0, 9);
        Long expectedActiveProperties = Long.valueOf(propertyDao.getAllActive(pageRequest).size());
        Long realActiveProperties = entityManager
                                    .createQuery("SELECT COUNT(p.id) FROM Property p", Long.class)
                                    .getSingleResult();

        Assert.assertNotEquals(realActiveProperties, expectedActiveProperties);

    }

    @Test
    public void getPropertyWithRelatedEntitiesHasAllRelatedEntitiesTest(){
        int propertyID = 1; //According to our schema.sql we have a property with ID 1, so it's correct to assume it exists
        Property maybeProperty;

        maybeProperty = propertyDao.getPropertyWithRelatedEntities(propertyID);
        Assert.assertNotNull(maybeProperty);
        Assert.assertEquals(propertyID, maybeProperty.getId());
        Assert.assertNotNull(maybeProperty.getNeighbourhood());
        Assert.assertNotNull(maybeProperty.getRules());
    }


    @Test
    @Transactional
    @Rollback
    public void changeStatusActuallyChangesPropertyStatusTest(){
        Property property = entityManager.find(Property.class, Long.valueOf(1));

        Assert.assertEquals(Availability.AVAILABLE, property.getAvailability());
        propertyDao.changeStatus(1);

        Assert.assertNotEquals(Availability.AVAILABLE, property.getAvailability());
        Assert.assertEquals(Availability.RENTED, property.getAvailability());

    }

    @Test
    @Transactional
    @Rollback
    public void undoInterestRemoveInterestsTest(){
        Long propertyId = Long.valueOf(1);
        User user = entityManager.find(User.class, Long.valueOf(1));
        Long interestedUsers = entityManager.createQuery("SELECT Count(i.id) FROM Interest i WHERE i.property=:property and i.user=:user", Long.class)
                .setParameter("property", entityManager.find(Property.class, propertyId))
                .setParameter("user", user)
                .getSingleResult();

        Assert.assertEquals(Long.valueOf(1), interestedUsers);
        propertyDao.undoInterest(Long.valueOf(1), user );

        interestedUsers = entityManager.createQuery("SELECT Count(i.id) FROM Interest i WHERE i.property=:property and i.user=:user", Long.class)
                .setParameter("property", entityManager.find(Property.class, propertyId))
                .setParameter("user", user)
                .getSingleResult();
        Assert.assertEquals(Long.valueOf(0), interestedUsers);

    }

    /*
    @Test
    @Transactional
    @Rollback
    public void showInterestCreatesRelationshipUserPropertyTest(){
        Long propertyId = Long.valueOf(2);
        User user = entityManager.find(User.class, Long.valueOf(1));
        Long interestedUsers = entityManager.createQuery("SELECT Count(i.id) FROM Interest i WHERE i.property=:property and i.user=:user", Long.class)
                                .setParameter("property", entityManager.find(Property.class, propertyId))
                                .setParameter("user", user)
                                .getSingleResult();

        Assert.assertEquals(Long.valueOf(0), interestedUsers);
        propertyDao.showInterest(propertyId, entityManager.find(User.class, Long.valueOf(1)));
        Assert.assertEquals(Long.valueOf(1), interestedUsers);
    }
    */

    @Test
    public void getInterestByPropAndUserReturnsInterestIfExistsTest(){
        Interest maybeInterest = propertyDao.getInterestByPropAndUser(1, entityManager.find(User.class, Long.valueOf(1)));

        Assert.assertNotNull(maybeInterest);
        Assert.assertEquals( 1, maybeInterest.getUser().getId());
        Assert.assertEquals(1, maybeInterest.getProperty().getId());
    }

    @Test
    public void getInterestByPropAndUserReturnsNullIfNotExistsTest(){
        Interest maybeInterest = propertyDao.getInterestByPropAndUser(4, entityManager.find(User.class, Long.valueOf(1)));

        Assert.assertNull(maybeInterest);
    }


    /*
    @Test
    @Transactional
    @Rollback
    public void deletePropertyRemovesPropertyFromDatabaseTest(){
        Long propertyId = Long.valueOf(1);
        Long propertyCount = entityManager.createQuery("SELECT Count(p.id) FROM Property p WHERE p.id=:id", Long.class)
                             .setParameter("id", propertyId)
                             .getSingleResult();

        Assert.assertEquals(Long.valueOf(1), propertyCount);
        propertyDao.delete(propertyId);
        propertyCount = entityManager.createQuery("SELECT Count(p.id) FROM Property p WHERE p.id=:id", Long.class)
                .setParameter("id", propertyId)
                .getSingleResult();

        Assert.assertEquals(Long.valueOf(0), propertyCount);
    }
*/




}
