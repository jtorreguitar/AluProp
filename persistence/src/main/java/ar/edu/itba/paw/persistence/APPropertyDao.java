package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Rule;
import ar.edu.itba.paw.model.enums.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.model.Property;

@Repository
public class APPropertyDao implements PropertyDao {

    private static final String TABLE_NAME = "properties";
    private final RowMapper<Property> ROW_MAPPER = (rs, rowNum)
        -> new Property.Builder()
            .withId(rs.getLong("id"))
            .withCapacity(rs.getInt("capacity"))
            .withCaption(rs.getString("caption"))
            .withDescription(rs.getString("description"))
            .withNeighbourhoodId(rs.getInt("neighbourhoodId"))
            .withPrice(rs.getFloat("price"))
            .withPrivacyLevel(rs.getBoolean("privacyLevel"))
            .withPropertyType(PropertyType.valueOf(rs.getString("propertyType")))
            .withMainImageId(rs.getLong("mainImageId"))
            .build();
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert interestJdbcInsert;
    @Autowired
    private NeighbourhoodDao neighbourhoodDao;
    @Autowired
    private PropertyRuleDao propertyRuleDao;
    @Autowired
    private APRuleDao ruleDao;
    @Autowired
    private InterestDao interestDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageDao imageDao;

    @Autowired
    public APPropertyDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        interestJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("interests")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public Property get(long id) {
        List<Property> list = jdbcTemplate.query("SELECT * FROM properties WHERE id = ?", getRowMapper(), id);
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }

    @Override
    public Collection<Property> getAll() {
        return jdbcTemplate.query("SELECT * FROM properties", getRowMapper());
    }

    @Override
    public boolean showInterest(long propertyId, User user) {
        final int rowsAffected = interestJdbcInsert
                .execute(generateArgumentsForInterestCreation(propertyId, user));
        return rowsAffected == 1;
    }

    private Map<String, Object> generateArgumentsForInterestCreation(long propertyId, User user) {
        final Map<String, Object> args = new HashMap<>();
        args.put("propertyId", propertyId);
        args.put("userId", user.getId());
        return args;
    }

    @Override
    public Property getPropertyWithRelatedEntities(long id) {
        Property property = get(id);
        return new Property.Builder()
                    .fromProperty(property)
                    .withNeighbourhood(neighbourhoodDao.get(property.getNeighbourhoodId()))
                    .withInterestedUsers(getInterestedUsers(id))
                    .withRules(getPropertyRules(id))
                    .build();
    }

    private Collection<Rule> getPropertyRules(long id) {
        return ruleDao.getAllAsStream()
                .filter(rule -> propertyRuleDao.getAll().stream()
                        .filter(pr -> pr.getPropertyId() == id)
                        .anyMatch(pr -> rule.getId() == pr.getId()))
                .collect(Collectors.toList());
    }

    private Collection<User> getInterestedUsers(long id) {
        return userDao.getAllAsStream()
                .filter(userShowedInterestInProperty(id))
                .collect(Collectors.toList());
    }

    private Predicate<User> userShowedInterestInProperty(long id) {
        return user -> interestDao.getAll().stream()
                            .filter(interest -> interest.getId() == id)
                            .anyMatch(interest -> user.getId() == interest.getId());
    }

    protected RowMapper<Property> getRowMapper() {
        return this.ROW_MAPPER;
    }
}
