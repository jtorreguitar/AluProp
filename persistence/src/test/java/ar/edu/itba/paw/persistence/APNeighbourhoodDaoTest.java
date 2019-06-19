package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Neighbourhood;
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
public class APNeighbourhoodDaoTest {
    private final static String NAME = "Palermo";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APNeighbourhoodDao neighbourhoodDao;

    @Test
    public void getNeighbourhoodTest() {
        Neighbourhood maybeNeighbourhood = neighbourhoodDao.get(1);

        Assert.assertNotNull(maybeNeighbourhood);
        Assert.assertEquals(NAME, maybeNeighbourhood.getName());
    }

    @Test
    public void getAllNeighbouthoodsTest() {
        Long expectedRowCount = entityManager
                                .createQuery("SELECT COUNT(n.id) FROM Neighbourhood n", Long.class)
                                .getSingleResult();
        int realRowCount = neighbourhoodDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }
}
