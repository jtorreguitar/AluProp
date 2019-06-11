package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.model.PropertyRule;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class APUserProposalDao implements UserProposalDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(long userId, long proposalId) {
        final UserProposal userProposal = new UserProposal(entityManager.find(User.class, userId),
                                                            entityManager.find(Proposal.class, proposalId));
        entityManager.merge(userProposal);
    }

    @Override
    public List<UserProposal> getByProposalId(long id) {
        TypedQuery<UserProposal> query = entityManager
                                        .createQuery("FROM UserProposal up WHERE up.proposal.id = :proposalId",
                                                        UserProposal.class);
        query.setParameter("proposalId", id);
        return query.getResultList();
    }
}
