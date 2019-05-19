package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import javafx.util.Pair;
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
    private UserDao userDao;

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
        proposal.getUsers().forEach(user -> userProposalDao.create(user.getId(), proposal.getId()));
    }

//    private List<User> getInvitedUsers(Proposal proposal){
//
//
//    }

    @Override
    public Proposal getById(long id) {
        List<Proposal> list = jdbcTemplate.query("SELECT * FROM proposals  WHERE proposals.id=? ", ROW_MAPPER, id);
        if(!list.isEmpty()){
            RowMapper<Pair<Long, Integer>> mapper = (rs, rowNum) -> new Pair<>(rs.getLong("userid"), rs.getInt("state"));
            List<Pair<Long, Integer>> invitedList = jdbcTemplate.query("SELECT * FROM userProposals  WHERE proposalid=? ", mapper, list.get(0).getId());
            list.get(0).setUsers(new ArrayList<>());
            list.get(0).setInvitedUserStates(new ArrayList<>());
            for (Pair<Long, Integer> pair: invitedList){
                list.get(0).getUsers().add(userDao.get(pair.getKey()));
                list.get(0).getInvitedUserStates().add(pair.getValue());
            }
            return list.get(0);
        }
        return null;
    }


}
