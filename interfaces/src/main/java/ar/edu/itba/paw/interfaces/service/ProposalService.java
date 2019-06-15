package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;

import java.util.Collection;
import java.util.List;

public interface ProposalService {
    Either<Proposal, List<String>> createProposal(Proposal proposal, long[] userIds);
    void delete(long id);
    Proposal getById(long id);
    Collection<Proposal> getAllProposalForUserId(long id);
    void setAccept(long userId, long proposalId);
    long setDecline(long userId, long proposalId);
}
