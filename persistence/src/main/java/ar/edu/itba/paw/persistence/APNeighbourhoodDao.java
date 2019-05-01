package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.NeighbourhoodDao;
import ar.edu.itba.paw.model.Neighbourhood;
import ar.edu.itba.paw.persistence.mappings.NeighbourhoodDatabaseMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class APNeighbourhoodDao implements NeighbourhoodDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APNeighbourhoodDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Neighbourhood get(long id) {
        final List<Neighbourhood> list = jdbcTemplate
                .query("SELECT * FROM neighbourhoods WHERE id = ?", getRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    private RowMapper<Neighbourhood> getRowMapper() {
        return NeighbourhoodDatabaseMapping.ROW_MAPPER;
    }
}
