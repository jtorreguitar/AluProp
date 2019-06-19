package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;
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
import java.util.Collection;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APUserDaoTest {

    private final static String NAME = "John";
    private final static String EMAIL = "johnTester@gmail.com";

    /* package */ final static String NAME2 = "Robert";
    /* package */ final static String EMAIL2 = "elReyDelTesting@gmail.com";
    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void getUserTest(){
        User maybeUser = userDao.get(1);

        Assert.assertNotNull(maybeUser);
        Assert.assertEquals(1, maybeUser.getId());
        Assert.assertEquals(NAME, maybeUser.getName());
        Assert.assertEquals(EMAIL, maybeUser.getEmail());

    }

    @Test
    public void getAllUsersTest(){
        Long expectedRowCount = entityManager
                            .createQuery("SELECT COUNT(u.id) FROM User u", Long.class)
                            .getSingleResult();
        int realRowCount = userDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }

    @Test
    public void getUserByEmailTest(){
        User maybeUser = userDao.getByEmail(EMAIL);

        Assert.assertNotNull(maybeUser);
        Assert.assertEquals(NAME, maybeUser.getName());
    }

    @Test
    public void createUserTest(){
        User expectedUser = userDao.create(Factories.userCreator());
        Assert.assertNotNull(expectedUser);
        Assert.assertEquals(EMAIL2,expectedUser.getEmail());
        Assert.assertEquals(NAME2, expectedUser.getName());
    }


    @Test
    public void userExistsByEmailWithExistingUserReturnsTrueTest(){
        System.out.println(entityManager
                .createQuery("SELECT COUNT(u.id) FROM User u", Long.class)
                .getSingleResult());
        boolean userExists = userDao.userExistsByEmail(EMAIL);

        Assert.assertTrue(userExists);
    }

    @Test
    public void userExistsByEmailWithNonExistingUserReturnsFalseTest(){
        boolean userExists = userDao.userExistsByEmail("IACTUALLYDONTEXISTS@gmail.com");

        Assert.assertFalse(userExists);
    }

    //You can see in the schema.sql that there is an user interested in property with ID 1
    @Test
    public void getUsersInterestedInPropertyInPropertyWithInterestedUsersTest(){
        Collection<User> interestedUsers = userDao.getUsersInterestedInProperty(1, new PageRequest(0, 9));

        Assert.assertNotNull(interestedUsers);
        Assert.assertEquals(1, interestedUsers.size());

    }

    @Test
    public void getUsersInterestedInPropertyInPropertyWithoutInterestedUsersTest(){
        Collection<User> interestedUsers = userDao.getUsersInterestedInProperty(3, new PageRequest(0, 9));

        Assert.assertNotNull(interestedUsers);
        Assert.assertEquals(0, interestedUsers.size());
    }

    @Test
    public void countReturnsNumberOfUsersInDatabase(){
        Long expectedNumberOfUsers = Long.valueOf(1);
        Long realNumberOfUsers = userDao.count();

        Assert.assertNotNull(expectedNumberOfUsers);
        Assert.assertEquals(expectedNumberOfUsers, realNumberOfUsers);
    }


}
