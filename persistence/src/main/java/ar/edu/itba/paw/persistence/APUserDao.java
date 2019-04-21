package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.CareerDao;
import ar.edu.itba.paw.interfaces.UniversityDao;
import ar.edu.itba.paw.model.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

import java.util.List;

@Repository
public class APUserDao extends APDao<User> implements UserDao {

    private static final String TABLE_NAME = "properties";
    private final RowMapper<User> ROW_MAPPER = (rs, rowNum)
            -> new User.Builder()
                .withId(rs.getLong("id"))
                .withBio(rs.getString("bio"))
                .withBirthDate(rs.getDate("birthDate"))
                .withCareerId(rs.getLong("careerId"))
                .withContactNumber(rs.getString("contactNumber"))
                .withEmail(rs.getString("email"))
                .withGender(Gender.valueOf(rs.getInt("gender")))
                .withLastName(rs.getString("lastName"))
                .withName(rs.getString("name"))
                .withUsername(rs.getString("username"))
                .withPasswordHash(rs.getString("passwordHash"))
                .withUniversityId(rs.getLong("universityId"))
                .build();

    @Autowired
    private CareerDao careerDao;
    @Autowired
    private UniversityDao universityDao;

    @Autowired
    public APUserDao(DataSource ds) {
        super(ds);
    }

    public User getByUsername(String username) {
        final List<User> list = getJdbcTemplate()
                .query("SELECT * FROM users WHERE username = ?", getRowMapper(), username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected RowMapper<User> getRowMapper() {
        return this.ROW_MAPPER;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
