package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.persistence.mappings.UserDatabaseMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class APUserDao extends APDao<User> implements UserDao {

    private static final String TABLE_NAME = "users";
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public APUserDao(DataSource ds) {
        super(ds);
        jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate())
                            .withTableName("users")
                            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Stream<User> getAllAsStream() {
        return getJdbcTemplate().query("SELECT * FROM users", getRowMapper()).stream();
    }

    public User getByEmail(String username) {
        final List<User> list = getJdbcTemplate()
                .query("SELECT * FROM users WHERE username = ?", getRowMapper(), username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User create(User user) {
        Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForUserCreation(user));
        return get(id.longValue());
    }

    public User getWithRelatedEntities(long id) {
        final List<User> list = getJdbcTemplate()
                .query(UserDatabaseMapping.USER_GET_BY_ID_WITH_RELATED_ENTITIES_QUERY,
                        UserDatabaseMapping.ROW_MAPPER_WITH_RELATED_ENTITIES_FOR_SINGLE_USER_QUERIES,
                        id);
        return list.isEmpty() ? null : list.get(0);
    }

    private Map<String,Object> generateArgumentsForUserCreation(User user) {
        Map<String,Object> ret = new HashMap<>();
        ret.put("bio", user.getBio());
        ret.put("email", user.getEmail());
        ret.put("passwordHash", user.getPasswordHash());
        ret.put("name", user.getName());
        ret.put("lastName", user.getLastName());
        ret.put("careerId", user.getCareerId());
        ret.put("universityId", user.getUniversityId());
        ret.put("Gender", user.getGender().getValue());
        ret.put("birthDate", user.getBirthDate());
        return ret;
    }

    @Override
    protected RowMapper<User> getRowMapper() {
        return UserDatabaseMapping.ROW_MAPPER;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
