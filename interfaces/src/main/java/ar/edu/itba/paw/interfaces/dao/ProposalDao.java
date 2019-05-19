package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Proposal;

import java.util.Collection;

public interface ProposalDao {
    Proposal create(Proposal proposal);
    Proposal getById(long id);

    Collection<Proposal> getAllProposalForUserId(long id);
}
