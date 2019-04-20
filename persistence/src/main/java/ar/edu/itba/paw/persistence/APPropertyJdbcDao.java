package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.PropertyDao;
import ar.edu.itba.paw.model.Property;

@Repository
public class APPropertyJdbcDao extends APDao<Property> implements PropertyDao {

    private static final String TABLE_NAME = "properties";
    private final RowMapper<Property> ROW_MAPPER = (rs, rowNum) -> new Property(rs.getInt("id"),
            rs.getString("caption"), rs.getString("description"), rs.getString("image"), rs.getString("area"));
    private final SimpleJdbcInsert interestJdbcInsert;

    @Autowired
    public APPropertyJdbcDao(DataSource ds) {
        super(ds);
        interestJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate())
                        .withTableName("interests")
                        .usingGeneratedKeyColumns("id");
    }

    @Override
    public boolean showInterest(int propertyId, String email, String description) {
        final int rowsAffected = interestJdbcInsert
                .execute(generateArguementsForInterestCreation(propertyId, email, description));
        return rowsAffected == 1 ? true : false;
    }

    private Map<String, Object> generateArguementsForInterestCreation(int propertyId, String email,
            String description) {
        final Map<String, Object> args = new HashMap<>();
        args.put("propertyId", propertyId);
        args.put("email", email);
        args.put("description", description);
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

    @Override
    protected SimpleJdbcInsert getJdbcInsert() {
        return this.interestJdbcInsert;
    }
}
