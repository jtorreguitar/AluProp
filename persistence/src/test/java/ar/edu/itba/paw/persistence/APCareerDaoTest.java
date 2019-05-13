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

import javax.sql.DataSource;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APCareerDaoTest {

    private final static String CAREER_NAME = "Ingenieria en Testing";
    @Autowired
    private DataSource ds;


    @Autowired
    private APCareerDao careerDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void getCareerTest() {
        Career maybeCareer = careerDao.get(1);
        Assert.assertNotNull(maybeCareer);
        Assert.assertEquals(CAREER_NAME, maybeCareer.getName());
    }

    @Test
    public void getAllCareersTest() {
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "careers");
        int realRowCount = careerDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }
}