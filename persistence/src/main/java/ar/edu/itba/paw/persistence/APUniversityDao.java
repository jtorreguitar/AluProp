package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UniversityDao;
import ar.edu.itba.paw.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class APUniversityDao extends APDao<University> implements UniversityDao {
    private RowMapper<University> ROW_MAPPER = (rs, rowNum)
        -> new University(rs.getLong("id"), rs.getString("name"));

    @Autowired
    public APUniversityDao(DataSource ds) {
        super(ds);
    }

    @Override
    protected String getTableName() {
        return "universities";
    }

    @Override
    protected RowMapper<University> getRowMapper() {
        return ROW_MAPPER;
    }
}
