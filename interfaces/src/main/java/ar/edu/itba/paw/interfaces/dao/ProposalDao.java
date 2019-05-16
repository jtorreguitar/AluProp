package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Proposal;

public interface ProposalDao {
    Proposal create(Proposal proposal);
    Proposal getById(long id);
}
