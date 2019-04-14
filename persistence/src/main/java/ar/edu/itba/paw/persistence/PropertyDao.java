package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.IPropertyDao;
import ar.edu.itba.paw.model.Property;

@Repository
public class PropertyDao implements IPropertyDao {

    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Property> ROW_MAPPER = 
        (rs, rowNum) -> new Property(rs.getInt("id"),
                                    rs.getString("caption"),
                                    rs.getString("description"),
                                    rs.getString("image"),
                                    rs.getString("area"));

    @Autowired
    public PropertyDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Property get(int id) {
        final List<Property> list = jdbcTemplate.query("SELECT * FROM properties WHERE id = ?",
                                                    ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Collection<Property> getAll() {
        final List<Property> list = jdbcTemplate.query("SELECT * FROM properties",
                                                    ROW_MAPPER);
        return list;
    }

}