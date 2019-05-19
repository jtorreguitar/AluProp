package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class APProposalService implements ProposalService {

    static final String PROPERTY_NOT_EXISTS = "The property for this proposal does not exist.";
    static final String CREATOR_NOT_EXISTS = "The creator of this proposal does not exist.";


    @Autowired
    private ProposalDao proposalDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PropertyDao propertyDao;

    private List<String> errors;

    @Override
    public Either<Proposal, List<String>> createProposal(Proposal proposal) {
        errors = new LinkedList<>();
        checkRelatedEntitiesExist(proposal);
        if(!errors.isEmpty())
            return Either.alternativeFrom(errors);
        return Either.valueFrom(proposalDao.create(proposal));
    }

    @Override
    public long delete(long id) {
        return 1;
    }

    private void checkRelatedEntitiesExist(Proposal proposal) {
        checkPropertyExists(proposal.getPropertyId());
        checkCreatorExists(proposal.getCreatorId());
    }

    private void checkPropertyExists(long propertyId) {
        if(propertyDao.get(propertyId) == null)
            errors.add(PROPERTY_NOT_EXISTS);
    }

    private void checkCreatorExists(long creatorId) {
        if(userDao.get(creatorId) == null)
            errors.add(CREATOR_NOT_EXISTS);
    }

    @Override
    public Proposal getById(long id) {
        return proposalDao.getById(id);
    }

    @Override
    public Collection<Proposal> getAllProposalForUserId(long id){
        return proposalDao.getAllProposalForUserId(id);
    }
}
