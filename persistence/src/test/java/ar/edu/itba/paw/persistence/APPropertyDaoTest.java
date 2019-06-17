package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APPropertyDaoTest {

    private final static String CAPTION = "el mejor depto";
    private final static String DESCRIPTION = "posta que el mejor depto";

    @Autowired
    private DataSource ds;

    @Autowired
    private PropertyDao propertyDao;


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);


    }

    @Test
    public void getPropertyTest(){
        int propertyID = 1;
        Property maybeProperty;

        maybeProperty = propertyDao.get(propertyID);
        Assert.assertNotNull(maybeProperty);
        Assert.assertEquals(propertyID, maybeProperty.getId());
        Assert.assertEquals(CAPTION, maybeProperty.getCaption());
        Assert.assertEquals(DESCRIPTION, maybeProperty.getDescription());
    }

    @Test
    public void getAllPropertiesTest(){
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "properties");
        int realRowCount = propertyDao.getAllActive(new PageRequest(0,0)).size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }
//
//    @Test
//    public void advancedSearchTest(){
//        int expectedCount =1;
//        List<Long> rules = new LinkedList<>();
//        rules.add(Long.valueOf(1));
//
//        List<Long> services = new LinkedList<>();
//        services.add(Long.valueOf(1));
//        int realCount = propertyDao.advancedSearch(new PageRequest(0, 0), "",rules, services, null, null).size();
//
//        Assert.assertEquals(expectedCount, realCount);
//
//    }


    /* TODO[IV]
    @Test
    public void getPropertyByDescriptionTest(){

    }
    */


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

    /* TODO[IV]: Should use a Mock User w/Mockito???
    @Test
    public void showInterestCreatesRelationshipUserPropertyTest(){
        boolean columnsWhereChanged = propertyDao.showInterest(1, 1);

    }
    */

}
