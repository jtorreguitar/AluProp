package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.dao.InterestDao;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

@Repository
public class APUserDao implements UserDao {

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
                .withRole(Role.valueOf(rs.getString("role")))
                .build();
    private final JdbcTemplate jdbcTemplate;
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
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                            .withTableName("users")
                            .usingGeneratedKeyColumns("id");
    }

    @Override
    public User get(long id) {
        final List<User> list = jdbcTemplate
                .query("SELECT * FROM users WHERE id = ?", ROW_MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User getByEmail(String email) {
        final List<User> list = jdbcTemplate
                .query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
    }

    @Override
    public User create(User user) {
        Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForUserCreation(user));
        return new User.Builder()
                    .fromUser(user)
                    .withId(id.longValue())
                    .build();
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
        ret.put("Gender", user.getGender().toString());
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
}
