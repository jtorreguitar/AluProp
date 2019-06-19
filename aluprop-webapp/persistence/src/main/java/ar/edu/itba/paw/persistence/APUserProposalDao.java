package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserProposal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class APUserProposalDao implements UserProposalDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(long userId, long proposalId) {
        final UserProposal userProposal = new UserProposal(entityManager.find(User.class, userId),
                                                            entityManager.find(Proposal.class, proposalId));
        entityManager.merge(userProposal);
    }
}
