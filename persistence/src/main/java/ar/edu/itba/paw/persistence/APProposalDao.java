package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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

    @Override
    public long delete(long id) {
        return jdbcTemplate.update("DELETE FROM proposals WHERE id=?", id);
    }

    private Map<String, Object> generateArgumentsForProposalCreation(Proposal proposal){
        Map<String, Object> map = new HashMap<>();
        map.put("propertyId", proposal.getPropertyId());
        map.put("creatorId", proposal.getCreatorId());
        return map;
    }

    private void createRelatedEntities(Proposal proposal){
        proposal.getUsers().forEach(user -> userProposalDao.create(user.getId(), proposal.getId()));
    }

    @Override
    public Proposal getById(long id) {
        List<Proposal> list = jdbcTemplate.query("SELECT * FROM proposals  WHERE id = ?", ROW_MAPPER, id);
        if(list.isEmpty()) return null;
        Proposal proposal = new Proposal.Builder()
                                .fromProposal(list.get(0))
                                .withUserProposals(userProposalDao.getByProposalId(list.get(0).getId()))
                                .build();
        return proposal;
    }

    @Override
    public Collection<Proposal> getAllProposalForUserId(long id){
        RowMapper<Proposal> mapper = (rs, rowNum) -> new Proposal.Builder()
                        .withCreatorId(rs.getLong("creatorid")).withPropertyId(rs.getLong("propertyid")).withId(rs.getLong("id")).build();
        List<Proposal> list = jdbcTemplate.query("SELECT * FROM proposals WHERE creatorid=? ", mapper, id);
        if (list.size() > 0)
            return list;
        return null;
    }

    @Override
    public long setAccept(long userId, long proposalId) {
        return jdbcTemplate.update("UPDATE userProposals SET state=1 WHERE proposalid=? AND userid=?", proposalId, userId);
    }

    @Override
    public long setDecline(long userId, long proposalId) {
        return jdbcTemplate.update("UPDATE userProposals SET state=2 WHERE proposalid=? AND userid=?", proposalId, userId);
    }
}
