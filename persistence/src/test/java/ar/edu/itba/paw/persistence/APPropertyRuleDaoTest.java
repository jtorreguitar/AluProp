package ar.edu.itba.paw.persistence;

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

import static org.junit.Assert.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APPropertyRuleDaoTest {


    @Autowired
    private DataSource ds;

    @Autowired
    private APPropertyRuleDao propertyRuleDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void getAllPropertyRulesTest() {
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyRules");
        int realRowCount = propertyRuleDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }

    @Test
    public void createPropertyRuleTest() {
        int previousRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyRules");
        propertyRuleDao.create(1, 2);
        int postRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyRules");

        assertEquals(previousRowCount+ 1 , postRowCount);
    }
}