package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.UserProposal;

import java.util.List;

public interface UserProposalDao {

    void create(long userId, long proposalId);

    List<UserProposal> getByProposalId(long id);
}
