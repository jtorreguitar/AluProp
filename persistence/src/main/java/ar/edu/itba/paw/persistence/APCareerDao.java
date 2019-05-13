package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class APCareerDao implements CareerDao {

    private RowMapper<Career> ROW_MAPPER = (rs, rownum)
        -> new Career(rs.getLong("id"), rs.getString("name"));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public APCareerDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Career get(long id) {
        List<Career> careers = jdbcTemplate.query("SELECT * FROM careers WHERE id = ?", ROW_MAPPER, id);
        return careers.isEmpty() ? null : careers.get(0);
    }

    @Override
    public Collection<Career> getAll() {
        return jdbcTemplate.query("SELECT * FROM careers", ROW_MAPPER);
    }
}
