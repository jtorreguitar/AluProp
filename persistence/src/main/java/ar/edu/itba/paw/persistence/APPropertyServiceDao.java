package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.PropertyServiceDao;
import ar.edu.itba.paw.model.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class APPropertyServiceDao implements PropertyServiceDao {

    private final RowMapper<PropertyService> ROW_MAPPER = (rs, rowNum)
            -> new PropertyService(rs.getLong("id"), rs.getLong("propertyId"), rs.getLong("serviceId"));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public APPropertyServiceDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("propertyServices")
                        .usingGeneratedKeyColumns("id");
    }


    @Override
    public Collection<PropertyService> getAll() {
        return jdbcTemplate.query("SELECT * FROM propertyServices", ROW_MAPPER);
    }

    @Override
    public void create(long serviceId, long propertyId) {
        Map<String, Object> args = new HashMap<>();
        args.put("serviceId", serviceId);
        args.put("propertyId", propertyId);
        jdbcInsert.execute(args);
    }
}
