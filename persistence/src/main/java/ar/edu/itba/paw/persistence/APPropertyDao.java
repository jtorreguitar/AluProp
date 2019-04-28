package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.model.Property;

@Repository
public class APPropertyDao extends APDao<Property> implements PropertyDao {

    private static final String TABLE_NAME = "properties";
    private final RowMapper<Property> ROW_MAPPER = (rs, rowNum)
        -> new Property.Builder()
            .withId(rs.getLong("id"))
            .withCapacity(rs.getInt("capacity"))
            .withCaption(rs.getString("caption"))
            .withDescription(rs.getString("description"))
            .withNeighbourhoodId(rs.getLong("neighbourhoodId"))
            .withPrice(rs.getFloat("price"))
            .withPrivacyLevel(rs.getBoolean("privacyLevel"))
            .withPropertyType(PropertyType.valueOf(rs.getInt("propertyType")))
            .build();
    private final SimpleJdbcInsert interestJdbcInsert;

    @Autowired
    public APPropertyDao(DataSource ds) {
        super(ds);
        interestJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate())
                        .withTableName("interests")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public boolean showInterest(int propertyId, User user) {
        final int rowsAffected = interestJdbcInsert
                .execute(generateArgumentsForInterestCreation(propertyId, user));
        return rowsAffected == 1;
    }

    private Map<String, Object> generateArgumentsForInterestCreation(int propertyId, User user) {
        final Map<String, Object> args = new HashMap<>();
        args.put("propertyId", propertyId);
        args.put("userId", user.getId());
        return args;
    }

    @Override
    protected RowMapper<Property> getRowMapper() {
        return this.ROW_MAPPER;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
