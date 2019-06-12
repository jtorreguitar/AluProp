package ar.edu.itba.paw.persistence;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.persistence.utilities.QueryUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class APPropertyDao implements PropertyDao {

    private final String GET_INTERESTS_OF_USER_QUERY = "FROM properties p WHERE EXISTS (FROM interests i WHERE i.property.id = p.id AND i.user.id = :userId)";
    private final String GET_PROPERTIES_OF_USER_QUERY = "FROM properties p WHERE p.owner.id = :ownerId";
    private final String GET_PROPERTY_BY_DESCRIPTION_QUERY = "FROM Property p WHERE p.description LIKE CONCAT('%',?1,'%')";
    private final String INTEREST_BY_PROP_AND_USER_QUERY = "FROM Interest i WHERE i.property.id = :propertyId AND i.user.id = :userId";

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
            .withAvailability(Availability.valueOf(rs.getString("availability")))
            .build();

    private final JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public APPropertyDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Property get(long id) {
        return entityManager.find(Property.class, id);
    }

    @Override
    public Collection<Property> getPropertyByDescription(PageRequest pageRequest, String description) {
        if(description.equals(""))
            return getAll(pageRequest);
        TypedQuery<Property> query = entityManager.createQuery(GET_PROPERTY_BY_DESCRIPTION_QUERY, Property.class);
        return QueryUtility.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    public Collection<Property> getAll(PageRequest pageRequest) {
        TypedQuery<Property> query = entityManager.createQuery("FROM Property", Property.class);
        return QueryUtility.makePagedQuery(query, pageRequest).getResultList();
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
    public void changeStatus(Property prop, long id) {
        Availability oldAvail = prop.getAvailability();
        Availability newAvail;
        switch(oldAvail){
            case AVAILABLE:
                newAvail=Availability.RENTED;
                break;
            case RENTED:
                newAvail=Availability.AVAILABLE;
                break;
            default:
                System.out.println("Error"); //TODO Remove
                return;
        }
        prop.setAvailability(newAvail);
        entityManager.merge(prop);
    }
    
    @Override
    public boolean showInterest(long propertyId, User user) {
        Interest interest = getInterestByPropAndUser(propertyId, user);
        if(interest != null) return false;
        interest = new Interest(entityManager.find(Property.class, propertyId), user);
        entityManager.persist(interest);
        return interest.getId() > 0;
    }

    @Override
    public boolean undoInterest(long propertyId, User user) {
        Interest interest = getInterestByPropAndUser(propertyId, user);
        if(interest != null) {
            entityManager.remove(interest);
            return true;
        }
        return false;
    }

    public Interest getInterestByPropAndUser(long propertyId, User user) {
        TypedQuery<Interest> query = entityManager.createQuery(INTEREST_BY_PROP_AND_USER_QUERY, Interest.class);
        query.setParameter("propertyId", propertyId);
        query.setParameter("userId", user.getId());
        Interest interest = query.getSingleResult();
        return interest;
    }

    @Transactional
    @Override
    public Property getPropertyWithRelatedEntities(long id) {
        Property property = entityManager.find(Property.class, id);
        if (property == null)
            return null;
        property.getRules().isEmpty();
        property.getServices().isEmpty();
        property.getImages().isEmpty();
        return property;
    }

    @Override
    @Transactional
    public Property create(Property property) {
        if(property != null)
            entityManager.persist(property);
        return property;
    }

    @Override
    public void delete(long id) {
        Property property = entityManager.find(Property.class, id);
        entityManager.remove(property);
    }

    @Transactional
    @Override
    public Collection<Property> getInterestsOfUser(long id) {
        User user = entityManager.find(User.class, id);
        user.getInterestedProperties().isEmpty();
        return user.getInterestedProperties();
    }

    @Transactional
    @Override
    public Collection<Property> getByOwnerId(long id) {
        User user = entityManager.find(User.class, id);
        user.getOwnedProperties().isEmpty();
        return user.getOwnedProperties();
    }

    @Override
    public Collection<Property> getInterestsOfUserPaged(long id, PageRequest pageRequest) {
        TypedQuery<Property> query = entityManager.createQuery(GET_INTERESTS_OF_USER_QUERY, Property.class);
        query.setParameter("userId", id);
        return QueryUtility.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(p.id) FROM Property p", Long.class).getSingleResult();
    }


}
