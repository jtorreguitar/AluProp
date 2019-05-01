package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static ar.edu.itba.paw.persistence.mappings.CareerDatabaseMapping.ROW_MAPPER;

@Repository
public class APCareerDao extends APDao<Career> implements CareerDao {

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
