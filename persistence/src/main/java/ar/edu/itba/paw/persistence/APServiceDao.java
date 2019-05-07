package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ServiceDao;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class APServiceDao implements ServiceDao {

    private RowMapper<Service> ROW_MAPPER = (rs, rowNum)
        -> new Service(rs.getLong("id"), rs.getString("name"));
    private JdbcTemplate jdbcTemplate;
    private String propertyServiceQuery = "SELECT * FROM services s WHERE EXISTS (SELECT * FROM propertyServices WHERE s.id = id AND propertyId = ?)";

    @Autowired
    public APServiceDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Service get(long id) {
        List<Service> services = jdbcTemplate.query("SELECT * FROM services WHERE id = ?", ROW_MAPPER);
        return services.isEmpty() ? null : services.get(0);
    }

    @Override
    public Collection<Service> getAll() {
        return jdbcTemplate.query("SELECT * FROM services", ROW_MAPPER);
    }

    @Override
    public Collection<Service> getServicesOfProperty(long propertyId) {
        return jdbcTemplate.query(propertyServiceQuery, ROW_MAPPER, propertyId);
    }
}
