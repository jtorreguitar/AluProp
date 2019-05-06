package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class APUniversityDao implements UniversityDao {

    private RowMapper<University> ROW_MAPPER = (rs, rowNum)
        -> new University(rs.getLong("id"), rs.getString("name"));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public APUniversityDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public University get(long id) {
        List<University> list = jdbcTemplate.query("SELECT * FROM universties WHERE id = ?", ROW_MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Collection<University> getAll() {
        return jdbcTemplate.query("SELECT * FROM universities", ROW_MAPPER);
    }
}
