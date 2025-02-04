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

    private static final String USER_PROPOSAL_BY_USER_AND_PROPOSAL = "FROM UserProposal up WHERE up.user.id = :userId AND up.proposal.id = :proposalId ORDER BY up.proposal.id DESC";
    private static final String PROPOSAL_BY_USER_AND_PROPERTY = "FROM Proposal p WHERE p.creator.id = :userId AND p.property.id = :propertyId ORDER BY p.id DESC";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Proposal create(Proposal proposal, long[] ids){
        if(ids != null)
            Arrays.stream(ids).forEach(id -> proposal.getUserProposals().add(UserProposal.fromUser(entityManager.find(User.class, id))));
        long creatorID = proposal.getCreator().getId();
        proposal.getUserProposals().add(UserProposal.fromHost(entityManager.find(User.class, creatorID)));
        entityManager.persist(proposal);
        return proposal;
    }

    @Override
    @Transactional
    public void dropProposal(long id) {
        Proposal proposal = entityManager.find(Proposal.class, id);
        if(proposal == null)
            return;
        proposal.setState(ProposalState.DROPPED);
        entityManager.merge(proposal);
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
    public long findDuplicateProposal(Proposal proposal, long[] userIds){
        Set<Long> invitedUserIds = new HashSet<>();
        if(userIds != null) {
            for(Long uid : userIds){
                invitedUserIds.add(uid);
            }
        }

        boolean foundIdentical;
        for(Proposal p : getAllProposalForUserIdAndPropertyId(proposal.getCreator().getId(), proposal.getProperty().getId())){
            foundIdentical=true;
            if(!(p.getState().equals(ProposalState.DECLINED) || p.getState().equals(ProposalState.CANCELED) || p.getState().equals(ProposalState.DROPPED))){
                if(invitedUserIds.size() == p.getUserProposals().size() - 1) {
                    for (UserProposal up : p.getUserProposals()) {
                        if (!invitedUserIds.contains(up.getUser().getId()) && up.getUser().getId() != proposal.getCreator().getId()) {
                            foundIdentical = false;
                            break;
                        }
                    }
                    if (foundIdentical) { //Found duplicate proposal
                        return p.getId();
                    }
                }
            }

        }

        return -1;
    }

    private Collection<Proposal> getAllProposalForUserIdAndPropertyId(long userId, long propertyId){
        return entityManager.createQuery(PROPOSAL_BY_USER_AND_PROPERTY, Proposal.class)
                .setParameter("userId", userId)
                .setParameter("propertyId", propertyId)
                .getResultList();
    }


    @Override
    @Transactional
    public Collection<Proposal> getAllProposalForUserId(long id){
        User user = entityManager.find(User.class, id);
        if(user == null) return new LinkedList<>();
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
    public void setState(long proposalId, ProposalState state) {
        Proposal prop = entityManager.find(Proposal.class, proposalId);
        prop.setState(state);
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
                                                .sorted((p1, p2) -> Long.compare(p2.getId(),p1.getId()))
                                                .collect(Collectors.toList());
        proposals.isEmpty();
        proposals.forEach(p -> p.getUserProposals().isEmpty());
        return proposals;
    }
}
