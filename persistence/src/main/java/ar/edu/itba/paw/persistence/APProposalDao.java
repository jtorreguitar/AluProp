package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.UserProposalState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class APProposalDao implements ProposalDao {

    private static final String USER_PROPOSAL_BY_USER_AND_PROPOSAL = "FROM UserProposal up WHERE up.user.id = :userId AND up.proposal.id = :proposalId";

    @PersistenceContext
    private EntityManager entityManager;

    public Proposal create(Proposal proposal){
        entityManager.merge(proposal);
        return proposal;
    }

    @Override
    public void delete(long id) {
        Proposal proposal = entityManager.find(Proposal.class, id);
        if(proposal == null)
            return;
        entityManager.remove(proposal);
    }

    @Override
    public Proposal getById(long id) {
        return entityManager.find(Proposal.class, id);
    }

    @Transactional
    @Override
    public Collection<Proposal> getAllProposalForUserId(long id){
        User user = entityManager.find(User.class, id);
        includeProposals(user);
        List<Proposal> list = new LinkedList<>();
        for(UserProposal up : user.getUserProposals())
            list.add(up.getProposal());
        return list;
    }

    private void includeProposals(User user) {
        user.getUserProposals().isEmpty();
        for(UserProposal up : user.getUserProposals())
            up.getProposal().getId();
    }

    @Override
    public void setAccept(long userId, long proposalId) {
        UserProposal userProposal = getUserProposalByUserAndProposalIds(userId, proposalId);
        userProposal.setState(UserProposalState.ACCEPTED);
        entityManager.merge(userProposal);
    }

    @Override
    // TODO: for crying out loud remove magic numbers
    public long setDecline(long userId, long proposalId) {
        UserProposal userProposal = getUserProposalByUserAndProposalIds(userId, proposalId);
        if(userProposal == null)
            return 0;
        userProposal.setState(UserProposalState.REJECTED);
        return 1;
    }

    private UserProposal getUserProposalByUserAndProposalIds(long userId, long proposalId) {
        TypedQuery<UserProposal> query = entityManager
                                        .createQuery(USER_PROPOSAL_BY_USER_AND_PROPOSAL, UserProposal.class);
        query.setParameter("userId", userId);
        query.setParameter("proposalId", proposalId);
        return query.getSingleResult();
    }
}
