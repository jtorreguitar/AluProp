package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Service
public class APProposalService implements ProposalService {

    static final String PROPERTY_NOT_EXISTS = "The property for this proposal does not exist.";
    static final String CREATOR_NOT_EXISTS = "The creator of this proposal does not exist.";
    static final String DUPLICATE_PROPOSAL = "Proposal duplicada amego";


    @Autowired
    private ProposalDao proposalDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PropertyDao propertyDao;

    @Autowired
    private UserProposalDao userProposalDao;

    private List<String> errors;

    @Override
    public Either<Proposal, List<String>> createProposal(Proposal proposal, long[] userIds) {
        errors = new LinkedList<>();
        checkRelatedEntitiesExist(proposal, userIds);
        if(!errors.isEmpty())
            return Either.alternativeFrom(errors);
        Proposal result = proposalDao.create(proposal, userIds);
//        for (long userId: userIds){
//            userProposalDao.create(userId, result.getId());
//        }
//

        return Either.valueFrom(result);
    }

    @Override
    public int delete(Proposal proposal, User u) {
        if(proposal.getCreator().getId() != u.getId())
            return HttpURLConnection.HTTP_NOT_FOUND;
        proposalDao.delete(proposal.getId());
        return HttpURLConnection.HTTP_OK;
    }

    private void checkRelatedEntitiesExist(Proposal proposal, long[] userIds) {
        checkPropertyExists(proposal.getProperty().getId());
        checkCreatorExists(proposal.getCreator().getId());
        checkProposalIsUnique(proposal, userIds);
    }


    private void checkProposalIsUnique(Proposal proposal, long[] userIds){
        if(!proposalDao.isProposalUnique(proposal, userIds)){
            errors.add(DUPLICATE_PROPOSAL);
        }
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
    public Proposal get(long id) {
        return proposalDao.get(id);
    }

    @Override
    public Proposal getWithRelatedEntities(long id) {
        return proposalDao.getWithRelatedEntities(id);
    }

    @Override
    public Collection<Proposal> getAllProposalForUserId(long id){
        return proposalDao.getAllProposalForUserId(id);
    }

    @Override
    public void setAcceptInvite(long userId, long proposalId) {
        proposalDao.setAcceptInvite(userId, proposalId);
    }

    @Override
    public long setDeclineInvite(long userId, long proposalId) {
        return proposalDao.setDeclineInvite(userId, proposalId);
    }

    @Override
    public void setAccept(long proposalId) {
        proposalDao.setAccept(proposalId);
    }

    @Override
    public void setDecline(long proposalId) {
        proposalDao.setDecline(proposalId);
    }

    @Override
    public Collection<Proposal> getProposalsForOwnedProperties(User profileUser) {
        return proposalDao.getProposalsForOwnedProperties(profileUser.getId());
    }
}
