package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.model.PropertyRule;
import ar.edu.itba.paw.model.UserProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class APUserProposalDao implements UserProposalDao {
    private RowMapper<UserProposal> ROW_MAPPER = (rs, rowNum)
            -> new UserProposal(rs.getLong("id"), rs.getLong("userId"), rs.getLong("proposalId"));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public APUserProposalDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("userProposals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void create(long userId, long proposalId) {
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("proposalId", proposalId);
        jdbcInsert.execute(args);
    }

}
