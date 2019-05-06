package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RuleDao;
import ar.edu.itba.paw.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.stream.Stream;

@Repository
public class APRuleDao implements RuleDao {

    private RowMapper<Rule> ROW_MAPPER = (rs, rowNum)
            -> new Rule(rs.getLong("id"), rs.getString("name"));
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APRuleDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Collection<Rule> getAll() {
        return jdbcTemplate.query("SELECT * FROM rules", ROW_MAPPER);
    }
}
