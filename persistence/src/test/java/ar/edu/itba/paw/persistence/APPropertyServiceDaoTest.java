package ar.edu.itba.paw.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APPropertyServiceDaoTest {

        @Autowired
        private DataSource ds;


        @Autowired
        private APPropertyServiceDao propertyServiceDao;

        private JdbcTemplate jdbcTemplate;

        @Before
        public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        }


        @Test
        public void getAllPropertyServiceTest() {
        int expectedRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyServices");
        int realRowCount = propertyServiceDao.getAll().size();

        Assert.assertNotEquals(0, realRowCount);
        Assert.assertEquals(expectedRowCount, realRowCount);
        }


        @Test
        public void createPropertyServiceTest() {
            int previousRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyServices");
            propertyServiceDao.create(1, 2);
            int postRowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "propertyServices");

            assertEquals(previousRowCount+ 1 , postRowCount);
        }

}