package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.dao.InterestDao;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class APUserDao extends APDao<User> implements UserDao {

    private static final String TABLE_NAME = "users";
    private final RowMapper<User> ROW_MAPPER = (rs, rowNum)
            -> new User.Builder()
                .withId(rs.getLong("id"))
                .withBio(rs.getString("bio"))
                .withBirthDate(rs.getDate("birthDate"))
                .withCareerId(rs.getLong("careerId"))
                .withContactNumber(rs.getString("contactNumber"))
                .withEmail(rs.getString("email"))
                .withGender(Gender.valueOf(rs.getString("gender")))
                .withLastName(rs.getString("lastName"))
                .withName(rs.getString("name"))
                .withPasswordHash(rs.getString("passwordHash"))
                .withUniversityId(rs.getLong("universityId"))
                .build();
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    private CareerDao careerDao;
    @Autowired
    private UniversityDao universityDao;
    @Autowired
    private InterestDao interestDao;
    @Autowired
    private PropertyDao propertyDao;

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

    public User getByEmail(String email) {
        final List<User> list = getJdbcTemplate()
                .query("SELECT * FROM users WHERE username = ?", getRowMapper(), email);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User create(User user) {
        Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForUserCreation(user));
        return get(id.longValue());
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
        ret.put("contactNumber", user.getContactNumber());
        return ret;
    }

    public User getUserWithRelatedEntities(long id) {
        User incompleteUser = get(id);
        return new User.Builder()
                .fromUser(incompleteUser)
                .withUniversity(universityDao.get(incompleteUser.getUniversityId()))
                .withCareer(careerDao.get(incompleteUser.getCareerId()))
                .withInterestedProperties(getInterestedProperties(id))
                .build();
    }

    private Collection<Property> getInterestedProperties(long id) {
        return propertyDao.getAll().stream()
                .filter(userIsInterestedInProperty(id))
                .collect(Collectors.toList());
    }

    private Predicate<Property> userIsInterestedInProperty(long id) {
        return p -> interestDao.getAll().stream()
                        .filter(i -> i.getUserId() == id)
                        .anyMatch(i -> i.getPropertyId() == p.getId());
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
