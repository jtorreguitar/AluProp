package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.PropertyRuleDao;
import ar.edu.itba.paw.model.PropertyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.stream.Stream;

@Repository
public class APPropertyRuleDao implements PropertyRuleDao {

    private RowMapper<PropertyRule> ROW_MAPPER = (rs, rowNum)
            -> new PropertyRule(rs.getLong("id"), rs.getLong("ruleId"), rs.getLong("propertyId"));
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APPropertyRuleDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Stream<PropertyRule> getAllAsStream() {
        return jdbcTemplate.query("SELECT * FROM propertyRules", ROW_MAPPER).stream();
    }
}
