package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Rule;
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
public class APRuleDaoTest {
    private final static String RULE_NAME = "No fumar mientras se escriben tests";
    @Autowired
    private DataSource ds;

    @Autowired
    private APRuleDao ruleDao;


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);


    }

    @Test
    public void getRuleTest() {
        Rule maybeRule = ruleDao.get(1);

        Assert.assertNotNull(maybeRule);
        Assert.assertEquals(RULE_NAME, maybeRule.getName());
    }

    @Test
    public void getAllRulesTest() {
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "rules");
        int realRowCount = ruleDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }
}