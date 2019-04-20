package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

@Repository
public class APUserDao extends APDao<User> implements UserDao {

    private static final String TABLE_NAME = "properties";
    private final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getInt("id"),
            rs.getString("email"), rs.getString("username"), rs.getString("passwordHash"));
    private final SimpleJdbcInsert interestJdbcInsert;

    @Autowired
    public APUserDao(DataSource ds) {
        super(ds);
        interestJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate())
                        .withTableName("users")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    protected RowMapper<User> getRowMapper() {
        return this.ROW_MAPPER;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected SimpleJdbcInsert getJdbcInsert() {
        return this.interestJdbcInsert;
    }
}
