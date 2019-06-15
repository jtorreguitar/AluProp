package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;

import java.util.Collection;
import java.util.List;

public interface ProposalService {
    Either<Proposal, List<String>> createProposal(Proposal proposal, long[] userIds);
    int delete(Proposal proposal, User u);
    Proposal get(long id);
    Proposal getWithRelatedEntities(long id);
    Collection<Proposal> getAllProposalForUserId(long id);
    void setAcceptInvite(long userId, long proposalId);
    long setDeclineInvite(long userId, long proposalId);
    void setAccept(long proposalId);
    void setDecline(long proposalId);

    Collection<Proposal> getProposalsForOwnedProperties(User profileUser);
}
