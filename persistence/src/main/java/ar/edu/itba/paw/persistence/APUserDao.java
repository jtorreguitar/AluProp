package ar.edu.itba.paw.persistence;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
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

@Repository
public class APUserDao implements UserDao {

    private static final String GET_USERS_BY_PROPERTY_QUERY = "SELECT * FROM users u WHERE EXISTS " +
                                                                    "(SELECT * FROM interests WHERE userId = u.id AND propertyId = ?)\n" +
                                                                "ORDER BY u.name\n" +
                                                                "LIMIT ? OFFSET ?";
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
        ret.put("role", user.getRole().toString());
        return ret;
    }

    @Override
    public User getUserWithRelatedEntitiesByEmail(String email) {
        User incompleteUser = getByEmail(email);
        return new User.Builder()
                .fromUser(incompleteUser)
                .withUniversity(universityDao.get(incompleteUser.getUniversityId()))
                .withCareer(careerDao.get(incompleteUser.getCareerId()))
                .build();
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return !jdbcTemplate.query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email).isEmpty();
    }

    @Override
    public PageResponse<User> getUsersInterestedInProperty(long id, PageRequest pageRequest) {
        Collection<User> data = jdbcTemplate.query((GET_USERS_BY_PROPERTY_QUERY),
                                                    ROW_MAPPER,
                                             id,
                                                    pageRequest.getPageSize(),
                                                    pageRequest.getPageNumber()*(pageRequest.getPageSize() + 1));
        RowMapper<Long> integerRowMapper = (rs, rowNum) -> rs.getLong("count");
        Long totalUsers = jdbcTemplate.query("SELECT COUNT(*) AS c FROM users", integerRowMapper).get(0);
        return new PageResponse<>(pageRequest, totalUsers, data);
    }

    @Override
    public boolean getUserIsInterestedInProperty(long userId, long propertyId){
        RowMapper<Integer> integerRowMapper = (rs, rowNum) -> rs.getInt("c");
        int totalUsers = jdbcTemplate.query("SELECT COUNT(*) AS c FROM interests WHERE propertyid = ? AND userid = ?", integerRowMapper, propertyId, userId).get(0);
        return totalUsers > 0;
    }
}
