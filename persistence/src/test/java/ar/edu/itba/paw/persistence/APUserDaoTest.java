package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.User;
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

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APUserDaoTest {

    private final static String NAME = "John";
    private final static String EMAIL = "johnTester@gmail.com";

    @Autowired
    private UserDao userDao;

    @Autowired
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
        int expectedRowCount = entityManager
                            .createQuery("SELECT COUNT(u.id) FROM users u", Integer.class)
                            .getSingleResult();
        int realRowCount = userDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }

    @Test
    public void getUserByEmailTest(){
        User maybeUser = userDao.getByEmail(EMAIL);
        Assert.assertNotNull(maybeUser);
        Assert.assertEquals(NAME, maybeUser.getName());
    }


}
