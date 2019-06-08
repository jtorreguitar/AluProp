package ar.edu.itba.paw.persistence;

import java.util.*;

import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
public class APPropertyDao implements PropertyDao {

    private final String GET_INTERESTS_OF_USER_QUERY = "SELECT * FROM properties p WHERE EXISTS (SELECT * FROM interests WHERE propertyId = p.id AND userId = ?)";
    private final String GET_PROPERTIES_OF_USER_QUERY = "SELECT * FROM properties p WHERE p.ownerid = ?";

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
            .withOwnerId(rs.getLong("ownerId"))
            .build();

    private final RowMapper<Interest> ROW_MAPPER_INTEREST = (rs, rowNum)
            -> new Interest(rs.getLong("id"), rs.getLong("propertyid"), rs.getLong("userid"));

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
    public Collection<Property> getPropertyByDescription(PageRequest pageRequest, String description){

        if(description.equals("")){
            //No search needed.
            return getAll(pageRequest);
        }
        String search_like = '%' + description + '%';
        return jdbcTemplate.query("SELECT * FROM properties WHERE description LIKE ? LIMIT ? OFFSET ?",
                                                ROW_MAPPER,
                                                search_like,
                                                pageRequest.getPageSize(),
                                                pageRequest.getPageNumber()*pageRequest.getPageSize());

    }

    @Override
    public Collection<Property> getAll(PageRequest pageRequest) {
        return jdbcTemplate.query("SELECT * FROM properties LIMIT ? OFFSET ?",
                            ROW_MAPPER,
                            pageRequest.getPageSize(),
                            pageRequest.getPageNumber()*pageRequest.getPageSize());
    }

    @Override
    public Collection<Property> advancedSearch(PageRequest pageRequest, String description, Integer propertyType, Integer neighborhood, Integer privacyLevel, Integer capacity, Float minPrice, Float maxPrice, long[] rules, long[] services) {
        if ( propertyType == -1 && neighborhood == -1
                && privacyLevel == -1 && capacity == 0
                && (minPrice == 0 && maxPrice == 0)
                && (rules == null || rules.length == 0)
                && (services == null || services.length== 0)){ //No advanced search needed. Just do plain search.
            return getPropertyByDescription(pageRequest, description);
        }


        StringBuilder SEARCH_CONDITION = new StringBuilder();
        boolean shouldAddAnd = false;

        if(description!=null && !description.equals("")){
            SEARCH_CONDITION.append("description LIKE '%" + description + "%'");
            shouldAddAnd = true;
        }

        if(propertyType != -1){
            if(shouldAddAnd){
                SEARCH_CONDITION.append(" AND ");
            }

            String prop;

            switch(propertyType.valueOf(propertyType)){
                case 0:
                    prop = "HOUSE";
                    break;
                case 1:
                    prop = "APARTMENT";
                    break;
                default:
                case 2:
                    prop= "LOFT";

            }
            SEARCH_CONDITION.append("propertyType= '" + prop + "'");
            shouldAddAnd = true;
        }

        if(neighborhood != -1){
            if(shouldAddAnd) SEARCH_CONDITION.append(" AND ");

            SEARCH_CONDITION.append("neighbourhoodid=" + neighborhood);
            shouldAddAnd=true;
        }

        if(privacyLevel!= null && privacyLevel != -1){
            if(shouldAddAnd) SEARCH_CONDITION.append(" AND ");
            boolean privacyLevelBool;
            privacyLevelBool = privacyLevel != 0;

            SEARCH_CONDITION.append("privacylevel=" + privacyLevelBool);
            shouldAddAnd = true;
        }

        if( capacity != null && capacity != 0){
            if(shouldAddAnd) SEARCH_CONDITION.append(" AND ");

            SEARCH_CONDITION.append("capacity=" + capacity);
            shouldAddAnd=true;
        }

        if(minPrice != null && maxPrice != null && maxPrice != 0){
            if(shouldAddAnd) SEARCH_CONDITION.append(" AND ");

            SEARCH_CONDITION.append("price > " + minPrice + " AND price < " + maxPrice);
            shouldAddAnd = true;
        }

        if(rules != null && rules.length != 0){
            for(Long ruleID : rules){
                if(shouldAddAnd){
                    SEARCH_CONDITION.append(" AND ");
                }
                SEARCH_CONDITION.append("ruleid=" + ruleID);
                shouldAddAnd = true;
            }
        }

        if(services != null && services.length != 0){
            for(Long serviceID : services){
                if(shouldAddAnd){
                    SEARCH_CONDITION.append(" AND ");
                }
                SEARCH_CONDITION.append("serviceid=" + serviceID);
                shouldAddAnd = true;
            }
        }
        StringBuilder QUERY = new StringBuilder();
        QUERY.append("SELECT * FROM properties ");

        if (services != null && services.length != 0){
            QUERY.append("INNER JOIN propertyServices on properties.id=propertyServices.propertyid ");
        }
        if(services != null && rules.length != 0){
            QUERY.append("INNER JOIN propertyRules on properties.id = propertyRules.propertyid ");
        }
        QUERY.append("WHERE " + SEARCH_CONDITION + " LIMIT ? OFFSET ?");

        try {
            List<Property> result = jdbcTemplate.query(QUERY.toString(),
                    ROW_MAPPER,
                    pageRequest.getPageSize(),
                    pageRequest.getPageNumber() * pageRequest.getPageSize());

            HashSet<Long> id_set = new HashSet<>();

            List<Property> result_unique = new LinkedList<>();
            for (Property prop : result) {
                if (!id_set.contains(prop.getId())) {
                    id_set.add(prop.getId());
                    result_unique.add(prop);
                }
            }

            return result_unique;
        }catch(Exception e){
            return new LinkedList<Property>();
        }
    }
    @Override
    public boolean showInterest(long propertyId, User user) {
//        if (interestExists(propertyId, user))
//            return false;
        final int rowsAffected = interestJdbcInsert
                .execute(generateArgumentsForInterestCreation(propertyId, user));
        return rowsAffected == 1;
    }

    @Override
    public boolean undoInterest(long propertyId, User user) {
        if (!interestExists(propertyId, user))
            return false;
        int rowsAffected = jdbcTemplate.update("DELETE FROM interests WHERE propertyid = ? AND userid = ?", propertyId, user.getId());
        return rowsAffected == 1;
    }

    //If the interest exists
    public boolean interestExists(long propertyId, User user) {
        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("count");
        long count = jdbcTemplate.query("SELECT COUNT(*) FROM interests WHERE propertyid = ? AND userid = ?", rowMapper, propertyId, user.getId()).get(0);
        return count > 0;
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
        if (property == null)
            return null;
        return new Property.Builder()
                    .fromProperty(property)
                    .withNeighbourhood(neighbourhoodDao.get(property.getNeighbourhoodId()))
                    .withRules(ruleDao.getRulesOfProperty(id))
                    .withServices(serviceDao.getServicesOfProperty(id))
                    .withMainImage(imageDao.get(property.getMainImageId()))
                    .withImages(imageDao.getByProperty(property.getId()))
                    .withOwner(userDao.get(property.getOwnerId()))
                    .withAvailability(true)
                    .build();
    }

    @Override
    @Transactional
    public Property create(Property property) {
        final Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForPropertyCreation(property));
        Property ret = new Property.Builder()
                            .fromProperty(property)
                            .withId(id.longValue())
                            .build();
        createRelatedEntities(ret);
        return ret;
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM properties WHERE id=?", id);
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
        args.put("ownerId", property.getOwnerId());
        return args;
    }

    private void createRelatedEntities(Property property) {
        imageDao.addProperty(property.getMainImageId(), property.getId());
        property.getImages().forEach(image -> imageDao.addProperty(image.getId(), property.getId()));
        property.getRules().forEach(rule -> propertyRuleDao.create(rule.getId(), property.getId()));
        property.getServices().forEach(service -> propertyServiceDao.create(service.getId(), property.getId()));
    }

    @Override
    public Collection<Property> getInterestsOfUser(long id) {
        return jdbcTemplate.query(GET_INTERESTS_OF_USER_QUERY, ROW_MAPPER, id);
    }

    @Override
    public Collection<Property> getByOwnerId(long id) {
        return jdbcTemplate.query(GET_PROPERTIES_OF_USER_QUERY, ROW_MAPPER, id);
    }

    @Override
    public PageResponse<Property> getInterestsOfUserPaged(long id, PageRequest pageRequest) {
        return null;
    }

    // TODO: look up how to return queries which just return an int instead of a property
    @Override
    public Long count() {
        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("count");
        return jdbcTemplate.query("SELECT COUNT(*) FROM properties", rowMapper).get(0);
    }


}
