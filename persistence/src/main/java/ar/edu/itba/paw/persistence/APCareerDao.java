package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CareerDao;
import ar.edu.itba.paw.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class APCareerDao extends APDao<Career> implements CareerDao {

    private RowMapper<Career> ROW_MAPPER = (rs, rownum)
        -> new Career(rs.getLong("id"), rs.getString("name"));

    @Autowired
    public APCareerDao(DataSource ds) {
        super(ds);
    }

    @Override
    protected String getTableName(){
        return "careers";
    }
    @Override
    protected RowMapper<Career> getRowMapper(){
        return ROW_MAPPER;
    }
}
