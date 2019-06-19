package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.University;
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

import static org.junit.Assert.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APUniversityDaoTest {
    private final static String NAME = "ITBA";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APUniversityDao universityDao;

    @Test
    public void getUniversityTest() {
        University maybeUniversity = universityDao.get(1);

        Assert.assertNotNull(maybeUniversity);
        Assert.assertEquals(NAME, maybeUniversity.getName());
    }

    @Test
    public void getAll() {
        Long expectedRowCount = entityManager
                            .createQuery("SELECT COUNT(u.id) FROM University u", Long.class)
                            .getSingleResult();
        int realRowCount = universityDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }
}
