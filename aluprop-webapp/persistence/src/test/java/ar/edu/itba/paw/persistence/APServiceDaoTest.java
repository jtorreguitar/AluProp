package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Service;
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
public class APServiceDaoTest {
    private final static String SERVICE_NAME = "Iluminación";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APServiceDao serviceDao;

    @Test
    public void getServiceTest() {
        Service maybeService = serviceDao.get(1);

        Assert.assertNotNull(maybeService);
        Assert.assertEquals(SERVICE_NAME , maybeService.getName());

    }

    @Test
    public void getAllServicesTest() {
        Long expectedRowCount = entityManager
                            .createQuery("SELECT COUNT(s.id) FROM Service s", Long.class)
                            .getSingleResult();
        int realRowCount = serviceDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount.intValue(), realRowCount);
    }
}
