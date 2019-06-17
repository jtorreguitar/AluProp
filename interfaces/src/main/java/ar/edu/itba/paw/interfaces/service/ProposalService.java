package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;

import java.util.Collection;
import java.util.List;

public interface ProposalService {
    Either<Proposal, List<String>> createProposal(Proposal proposal, long[] userIds);
    int delete(long id);
    Proposal get(long id);
    Proposal getWithRelatedEntities(long id);
    Collection<Proposal> getAllProposalForUserId(long id);
    int setAcceptInvite(long proposalId);
    int setDeclineInvite(long proposalId);
    int setAccept(long proposalId);
    int setDecline(long proposalId);

    Collection<Proposal> getProposalsForOwnedProperties(User profileUser);
    long findDuplicateProposal(Proposal proposal, long[] userIds);
}
