package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ProposalState;
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
    private static final String PROPOSALS_ON_OWNED_PROPERTYIES = "FROM Proposal p " +
                                                                    "WHERE (p.state != 'PENDING') " +
                                                                    "AND p.property IN ( SELECT op " +
                                                                                        "FROM User u " +
                                                                                        "LEFT OUTER JOIN u.ownedProperties op " +
                                                                                        "WHERE u.id = :id)";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Proposal create(Proposal proposal, long[] ids){
        Arrays.stream(ids).forEach(id -> proposal.getUserProposals().add(UserProposal.fromUser(entityManager.find(User.class, id))));
        entityManager.persist(proposal);
        return proposal;
    }

    @Override
    @Transactional
    public void delete(long id) {
        Proposal proposal = entityManager.find(Proposal.class, id);
        if(proposal == null)
            return;
        proposal.setState(ProposalState.DROPPED);
        entityManager.persist(proposal);
    }

    @Override
    @Transactional
    public Proposal get(long id) {
        Proposal prop = entityManager.find(Proposal.class, id);
        if (prop != null) prop.getUserProposals().isEmpty();
        return prop;
    }

    @Override
    @Transactional
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
        for(UserProposal up: user.getUserProposals())
            if (up != null && up.getProposal() != null)
                up.getProposal().getId();
    }

    @Override
    @Transactional
    public void setAcceptInvite(long userId, long proposalId) {
        UserProposal userProposal = getUserProposalByUserAndProposalIds(userId, proposalId);
        userProposal.setState(UserProposalState.ACCEPTED);
        entityManager.merge(userProposal);
    }

    @Override
    @Transactional
    public void setDeclineInvite(long userId, long proposalId) {
        UserProposal userProposal = getUserProposalByUserAndProposalIds(userId, proposalId);
        Proposal proposal = entityManager.find(Proposal.class, proposalId);
        if(userProposal == null || proposal == null)
            return;
        userProposal.setState(UserProposalState.REJECTED);
        proposal.setState(ProposalState.CANCELED);
    }

    @Override
    @Transactional
    public void setAccept(long proposalId) {
        Proposal prop = entityManager.find(Proposal.class, proposalId);
        prop.setState(ProposalState.ACCEPTED);
        entityManager.merge(prop);
    }

    @Override
    @Transactional
    public void setDecline(long proposalId) {
        Proposal prop = entityManager.find(Proposal.class, proposalId);
        prop.setState(ProposalState.DECLINED);
        entityManager.merge(prop);
    }

    private UserProposal getUserProposalByUserAndProposalIds(long userId, long proposalId) {
        TypedQuery<UserProposal> query = entityManager
                                        .createQuery(USER_PROPOSAL_BY_USER_AND_PROPOSAL, UserProposal.class);
        query.setParameter("userId", userId);
        query.setParameter("proposalId", proposalId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Proposal getWithRelatedEntities(long id) {
        final Proposal proposal = entityManager.find(Proposal.class, id);
        if (proposal == null)
            return null;
        proposal.getUsers().isEmpty();
        proposal.getUserProposals().isEmpty();
        return proposal;
    }

    @Override
    @Transactional
    public Collection<Proposal> getProposalsForOwnedProperties(long id) {
        final User owner = entityManager.find(User.class, id);
        Collection<Proposal> proposals = owner.getOwnedProperties().stream()
                                                .flatMap(property -> property.getProposals().stream())
                                                .filter(proposal -> proposal.getState() != ProposalState.PENDING)
                                                .collect(Collectors.toList());
        proposals.isEmpty();
        proposals.forEach(p -> p.getUserProposals().isEmpty());
        return proposals;
    }
}
