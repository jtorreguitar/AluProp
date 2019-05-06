package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Rule;
import ar.edu.itba.paw.model.enums.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final SimpleJdbcInsert jdbcInsert;
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
    private ServiceDao serviceDao;
    @Autowired
    private PropertyServiceDao propertyServiceDao;

    @Autowired
    public APPropertyDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                        .withTableName("properties")
                        .usingGeneratedKeyColumns("id");
        interestJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("interests")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public Property get(long id) {
        List<Property> list = jdbcTemplate.query("SELECT * FROM properties WHERE id = ?", ROW_MAPPER, id);
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }

    @Override
    public Collection<Property> getAll() {
        return jdbcTemplate.query("SELECT * FROM properties", ROW_MAPPER);
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
                    .withInterestedUsers(getRelatedEntities(userShowedInterestInProperty(id), userDao.getAll().stream()))
                    .withRules(getRelatedEntities(isRuleOfProperty(id), ruleDao.getAll().stream()))
                    .withServices(getRelatedEntities(isServiceOfProperty(id), serviceDao.getAll().stream()))
                    .withImages(imageDao.getByProperty(property.getId()))
                    .build();
    }

    private <T> Collection<T> getRelatedEntities(Predicate<T> isRelated, Stream<T> entities) {
        return entities.filter(isRelated).collect(Collectors.toList());
    }

    private Predicate<Service> isServiceOfProperty(long id) {
        return service -> propertyServiceDao.getAll().stream()
                .filter(ps -> ps.getPropertyId() == id)
                .anyMatch(ps -> service.getId() == ps.getId());
    }

    private Predicate<Rule> isRuleOfProperty(long id) {
        return rule -> propertyRuleDao.getAll().stream()
                .filter(pr -> pr.getPropertyId() == id)
                .anyMatch(pr -> rule.getId() == pr.getId());
    }

    private Predicate<User> userShowedInterestInProperty(long id) {
        return user -> interestDao.getAll().stream()
                            .filter(interest -> interest.getId() == id)
                            .anyMatch(interest -> user.getId() == interest.getId());
    }

    @Override
    public Property create(Property property) {
        final Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForPropertyCreation(property));
        Property ret = new Property.Builder()
                            .fromProperty(property)
                            .withId(id.longValue())
                            .build();
        createRelatedEntities(ret);
        return ret;
    }

    private Map<String, Object> generateArgumentsForPropertyCreation(Property property) {
        Map<String, Object> args = new HashMap<>();
        args.put("caption", property.getCaption());
        args.put("description", property.getDescription());
        args.put("capacity", property.getCapacity());
        args.put("price", property.getPrice());
        args.put("mainImageId", property.getMainImageId());
        args.put("neighbourhoodId", property.getNeighbourhoodId());
        args.put("privacyLevel", property.getPrivacyLevel());
        args.put("propertyType", property.getPropertyType().toString());
        return args;
    }

    private void createRelatedEntities(Property property) {
        imageDao.addProperty(property.getMainImageId(), property.getId());
        property.getImages().forEach(image -> imageDao.addProperty(image.getId(), property.getId()));
        property.getRules().forEach(rule -> propertyRuleDao.create(rule.getId(), property.getId()));
        property.getServices().forEach(service -> propertyServiceDao.create(service.getId(), property.getId()));
    }
}
