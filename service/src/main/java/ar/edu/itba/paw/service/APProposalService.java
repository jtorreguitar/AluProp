package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.UserProposalDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class APProposalService implements ProposalService {

    static final String PROPERTY_NOT_EXISTS = "The property for this proposal does not exist.";
    static final String CREATOR_NOT_EXISTS = "The creator of this proposal does not exist.";

    private final static String DELETE_SUBJECT_CODE= "notifications.proposals.deleted.subject";
    private final static String DELETE_BODY_CODE = "notifications.proposals.deleted";

    private final static String SENT_SUBJECT_CODE= "notifications.proposals.sent.subject";
    private final static String SENT_BODY_CODE = "notifications.proposals.sent";

    private final static String SENT_HOST_SUBJECT_CODE= "notifications.proposals.hostProposal.subject";
    private final static String SENT_HOST_BODY_CODE= "notifications.proposals.hostProposal";

    private final static String DECLINE_SUBJECT_CODE= "notifications.proposals.declined.subject";
    private final static String DECLINE_BODY_CODE = "notifications.proposals.declined";

    @Autowired
    private ProposalDao proposalDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    private List<String> errors;

    @Override
    public Either<Proposal, List<String>> createProposal(Proposal proposal, long[] userIds) {
        errors = new LinkedList<>();
        checkRelatedEntitiesExist(proposal);
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
    public int delete(long id) {
        Proposal proposal = proposalDao.get(id);
        User u = userService.getCurrentlyLoggedUser();
        if(proposal.getCreator().getId() != u.getId())
            return HttpURLConnection.HTTP_NOT_FOUND;
        notificationService.sendNotifications(DELETE_SUBJECT_CODE, DELETE_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());
        proposalDao.delete(proposal.getId());
        return HttpURLConnection.HTTP_OK;
    }

    private void checkRelatedEntitiesExist(Proposal proposal) {
        checkPropertyExists(proposal.getProperty().getId());
        checkCreatorExists(proposal.getCreator().getId());
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
    public int setAcceptInvite(long proposalId) {
        User u = userService.getCurrentlyLoggedUser();
        Proposal proposal = proposalDao.get(proposalId);
        if (!userIsInvitedToProposal(u, proposal))
            return HttpURLConnection.HTTP_FORBIDDEN;
        proposalDao.setAcceptInvite(u.getId(), proposalId);
        sendProposalAcceptanceNotifications(u, proposal);
        return HttpURLConnection.HTTP_OK;
    }

    private void sendProposalAcceptanceNotifications(User u, Proposal proposal) {
        if (proposal.isCompletelyAccepted()){
            User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
            proposal.getUsers().add(creator);
            notificationService.sendNotifications(SENT_SUBJECT_CODE, SENT_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());

            Property property = propertyDao.getPropertyWithRelatedEntities(proposal.getProperty().getId());
            notificationService.sendNotification(SENT_HOST_SUBJECT_CODE, SENT_HOST_BODY_CODE, "/proposal/" + proposal.getId(), property.getOwner());
        }
    }

    private boolean userIsInvitedToProposal(User user, Proposal proposal){
        for (User invitedUser: proposal.getUsers())
            if (invitedUser.getId() == user.getId())
                return true;
        return false;
    }

    @Override
    public int setDeclineInvite(long proposalId) {
        Proposal proposal = proposalDao.getWithRelatedEntities(proposalId);
        User currentUser = userService.getCurrentlyLoggedUser();
        if(proposal == null)
            return HttpURLConnection.HTTP_NOT_FOUND;
        if (!userIsInvitedToProposal(currentUser, proposal))
            return HttpURLConnection.HTTP_FORBIDDEN;
        User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
        proposal.getUsers().add(creator);
        delete(proposalId);
        notificationService.sendNotifications(DECLINE_SUBJECT_CODE, DECLINE_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), currentUser.getId());
        proposalDao.setDeclineInvite(currentUser.getId(), proposalId);
        return HttpURLConnection.HTTP_OK;
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
