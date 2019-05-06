package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.PropertyRuleDao;
import ar.edu.itba.paw.model.PropertyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class APPropertyRuleDao implements PropertyRuleDao {

    private RowMapper<PropertyRule> ROW_MAPPER = (rs, rowNum)
            -> new PropertyRule(rs.getLong("id"), rs.getLong("ruleId"), rs.getLong("propertyId"));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public APPropertyRuleDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                                .withTableName("propertyRules")
                                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Collection<PropertyRule> getAll() {
        return jdbcTemplate.query("SELECT * FROM propertyRules", ROW_MAPPER);
    }

    @Override
    public void create(long ruleId, long propertyId) {
        Map<String, Object> args = new HashMap<>();
        args.put("ruleId", ruleId);
        args.put("propertyId", propertyId);
        jdbcInsert.execute(args);
    }
}
