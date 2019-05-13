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

import javax.sql.DataSource;

import static org.junit.Assert.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APServiceDaoTest {
    private final static String SERVICE_NAME = "Iluminación";

    @Autowired
    private DataSource ds;

    @Autowired
    private APServiceDao serviceDao;


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);


    }
    @Test
    public void getServiceTest() {
        Service maybeService = serviceDao.get(1);

        Assert.assertNotNull(maybeService);
        Assert.assertEquals(SERVICE_NAME , maybeService.getName());

    }

    @Test
    public void getAllServicesTest() {
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "services");
        int realRowCount = serviceDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
    }
}