package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Proposal;

import java.util.Collection;

public interface ProposalDao {
    Proposal create(Proposal proposal, long[] userIds);
    void delete(long id);
    Proposal getById(long id);

    Collection<Proposal> getAllProposalForUserId(long id);

    void setAccept(long userId, long proposalId);

    long setDecline(long userId, long proposalId);
}
