package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.IPropertyDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;

@Repository
public class PropertyJdbcDao implements PropertyDao {

    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Property> ROW_MAPPER = (rs, rowNum) -> new Property(rs.getInt("id"),
            rs.getString("caption"), rs.getString("description"), rs.getString("image"), rs.getString("area"));
    private final RowMapper<Interest> ROW_MAPPER_INTEREST = (rs, rowNum) -> new Interest(rs.getInt("id"),
            rs.getInt("propertyId"), rs.getString("description"), rs.getString("email"));
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public PropertyDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                            .withTableName("interests")
                            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Property get(int id) {
        final List<Property> list = jdbcTemplate.query("SELECT * FROM properties WHERE id = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Collection<Property> getAll() {
        final List<Property> list = jdbcTemplate.query("SELECT * FROM properties", ROW_MAPPER);
        return list;
    }

    @Override
    public Interest interest(int propertyId, String email, String description) {
        final Map<String, Object> args = new HashMap<>();
        args.put("propertyId", propertyId);
        args.put("email", email);
        args.put("description", description);
        final Number id = jdbcInsert.executeAndReturnKey(args);
        final List<Interest> list = jdbcTemplate.query("SELECT * FROM interests WHERE id = ?", ROW_MAPPER_INTEREST, id.longValue());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}