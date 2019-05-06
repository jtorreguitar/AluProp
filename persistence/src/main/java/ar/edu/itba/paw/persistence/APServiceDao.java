package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ServiceDao;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class APServiceDao implements ServiceDao {

    private RowMapper<Service> ROW_MAPPER = (rs, rowNum)
        -> new Service(rs.getLong("id"), rs.getString("name"));
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APServiceDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Collection<Service> getAll() {
        return jdbcTemplate.query("SELECT * FROM services", ROW_MAPPER);
    }
}
