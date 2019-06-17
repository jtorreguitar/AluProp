package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Proposal;

import java.util.Collection;

public interface ProposalDao {
    Proposal create(Proposal proposal, long[] userIds);
    void delete(long id);
    Proposal get(long id);
    Proposal getWithRelatedEntities(long id);
    Collection<Proposal> getAllProposalForUserId(long id);

    void setAcceptInvite(long userId, long proposalId);
    void setDeclineInvite(long userId, long proposalId);

    void setAccept(long proposalId);
    void setDecline(long proposalId);

    boolean isProposalUnique(Proposal p, long[] userIds);
    Collection<Proposal> getProposalsForOwnedProperties(long id);
}
