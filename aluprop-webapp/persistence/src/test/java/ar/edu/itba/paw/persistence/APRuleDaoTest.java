package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RuleDao;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APRuleDaoTest {

    private final static String RULE_NAME = "No fumar mientras se escriben tests";
    private final static int PROPERTY_ID = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RuleDao ruleDao;

    @Test
    public void getRuleTest() {
        Rule maybeRule = ruleDao.get(1);

        Assert.assertNotNull(maybeRule);
        Assert.assertEquals(RULE_NAME, maybeRule.getName());
    }

    @Test
    public void getAllRulesTest() {
        Long expectedRowCount = entityManager
                            .createQuery("SELECT COUNT(r.id) FROM Rule r", Long.class)
                            .getSingleResult();
        int realRowCount = ruleDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }

    @Test
    public void getRulesOfPropertyTest() {
        List<Rule> rules = new ArrayList<>(ruleDao.getRulesOfProperty(PROPERTY_ID));
        Assert.assertNotNull(rules);
        Assert.assertFalse(rules.isEmpty());
        Assert.assertEquals(rules.get(0).getName(), RULE_NAME);
    }
}
