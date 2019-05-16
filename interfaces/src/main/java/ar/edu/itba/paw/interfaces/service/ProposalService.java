package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface ProposalService {
    Either<Proposal, List<String>> createProposal(Proposal proposal);
    Proposal getById(long id);
}
