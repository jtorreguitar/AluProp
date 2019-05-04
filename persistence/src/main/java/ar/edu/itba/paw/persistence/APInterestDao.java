package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.InterestDao;
import ar.edu.itba.paw.model.Interest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;

@Repository
public class APInterestDao implements InterestDao {

    private RowMapper<Interest> ROW_MAPPER = (rs, rowNum)
            -> new Interest(rs.getLong("id"), rs.getLong("propertyId"), rs.getLong("userId"));
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APInterestDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Collection<Interest> getAll() {
        return jdbcTemplate.query("SELECT * FROM interests", getRowMapper());
    }

    private RowMapper<Interest> getRowMapper() {
        return ROW_MAPPER;
    }
}
