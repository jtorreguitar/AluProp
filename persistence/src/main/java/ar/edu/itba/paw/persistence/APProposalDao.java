package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class APProposalDao implements ProposalDao {

    private RowMapper<Proposal> ROW_MAPPER = (rs, rowNum)
            -> new Proposal.Builder()
            .withId(rs.getLong("id"))
            .withPropertyId(rs.getLong("propertyId"))
            .withCreatorId(rs.getLong("creatorId"))
            .build();
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    private UserProposalDao userProposalDao;

    @Autowired
    public APProposalDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("proposals")
                .usingGeneratedKeyColumns("id");
    }

    public Proposal create(Proposal proposal){
        final Number id = jdbcInsert.executeAndReturnKey(generateArgumentsForProposalCreation(proposal));
        Proposal ret = new Proposal.Builder()
                .fromProposal(proposal)
                .withId(id.longValue())
                .build();
        createRelatedEntities(ret);
        return ret;
    }

    private Map<String, Object> generateArgumentsForProposalCreation(Proposal proposal){
        Map<String, Object> map = new HashMap<>();
        map.put("propertyId", proposal.getPropertyId());
        map.put("creatorId", proposal.getCreatorId());
        return map;
    }

    private void createRelatedEntities(Proposal proposal){
        System.out.println("DSADASDASD");
        for (User user:proposal.getUsers())
            System.out.println(user.getId());
        proposal.getUsers().forEach(user -> userProposalDao.create(user.getId(), proposal.getId()));
    }

    @Override
    public Proposal getById(long id) {
        List<Proposal> list = jdbcTemplate.query("SELECT * FROM properties WHERE id = ?", ROW_MAPPER, id);
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }


}
