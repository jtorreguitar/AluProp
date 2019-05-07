package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RuleDao;
import ar.edu.itba.paw.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class APRuleDao implements RuleDao {

    private RowMapper<Rule> ROW_MAPPER = (rs, rowNum)
            -> new Rule(rs.getLong("id"), rs.getString("name"));
    private JdbcTemplate jdbcTemplate;

    private String rulesOfPropertyQuery = "SELECT * FROM rules r WHERE EXISTS (SELECT * FROM propertyRules WHERE ruleId = r.id and propertyId = ?)";

    @Autowired
    public APRuleDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Rule get(long id) {
        List<Rule> rules = jdbcTemplate.query("SELECT * FROM rules WHERE id = ?", ROW_MAPPER, id);
        return rules.isEmpty() ? null : rules.get(0);
    }

    @Override
    public Collection<Rule> getAll() {
        return jdbcTemplate.query("SELECT * FROM rules", ROW_MAPPER);
    }

    @Override
    public Collection<Rule> getRulesOfProperty(long propertyId) {
        return jdbcTemplate.query(rulesOfPropertyQuery, ROW_MAPPER, propertyId);
    }
}
