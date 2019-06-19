package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Career;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APCareerDaoTest {

    private final static String CAREER_NAME = "Ingenieria en Testing";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APCareerDao careerDao;

    @Test
    public void getCareerTest() {
        Career maybeCareer = careerDao.get(1);
        Assert.assertNotNull(maybeCareer);
        Assert.assertEquals(CAREER_NAME, maybeCareer.getName());
    }

    @Test
    public void getAllCareersTest() {
        Long expectedRowCount = entityManager
                                .createQuery("SELECT COUNT(c.id) FROM Career c", Long.class)
                                .getSingleResult();
        int realRowCount = careerDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }
}
