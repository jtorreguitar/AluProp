package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.UserProposalState;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.utilities.NotificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("guest")
public class GuestController {

    private final static String DELETE_SUBJECT_CODE= "notifications.proposals.deleted.subject";
    private final static String DELETE_BODY_CODE = "notifications.proposals.deleted";

    private final static String DECLINE_SUBJECT_CODE= "notifications.proposals.declined.subject";
    private final static String DECLINE_BODY_CODE = "notifications.proposals.declined";

    private final static String SENT_SUBJECT_CODE= "notifications.proposals.sent.subject";
    private final static String SENT_BODY_CODE = "notifications.proposals.sent";

    private final static String SENT_HOST_SUBJECT_CODE= "notifications.proposals.hostProposal.subject";
    private final static String SENT_HOST_BODY_CODE= "notifications.proposals.hostProposal";

    @Autowired
    private NavigationUtility navigationUtility;
    @Autowired
    private NotificationUtility notificationUtility;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/delete/{proposalId}", method = RequestMethod.POST )
    public ModelAndView delete(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        final User u = navigationUtility.getCurrentlyLoggedUser();
        final Proposal proposal = proposalService.getWithRelatedEntities(proposalId);
        final int statusCode = proposalService.delete(proposal, u);
        if(statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
            mav.setViewName("404");
            return mav;
        }
        Collection<User> retrieveUsers = proposal.getUsers();
        notificationUtility.sendNotifications(DELETE_SUBJECT_CODE, DELETE_BODY_CODE, "/proposal/" + proposal.getId(), retrieveUsers, u.getId());
        mav.setViewName("successfulDelete");
        return mav;
    }

    @RequestMapping(value = "/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView accept(HttpServletRequest request, @PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        User u = navigationUtility.getCurrentlyLoggedUser();
        Proposal proposal = proposalService.get(proposalId);
        if (!userIsInvitedToProposal(u, proposal))
            return new ModelAndView("403").addObject("currentUser", u);
        proposalService.setAcceptInvite(u.getId(), proposalId);
        proposal = proposalService.get(proposalId);
        if (proposal.isCompletelyAccepted()){
            User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
            proposal.getUsers().add(creator);
            notificationUtility.sendNotifications(SENT_SUBJECT_CODE, SENT_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());

            Property property = propertyService.getPropertyWithRelatedEntities(proposal.getProperty().getId());
            List<User> owner = new ArrayList<>();
            owner.add(property.getOwner());
            notificationUtility.sendNotifications(SENT_HOST_SUBJECT_CODE, SENT_HOST_BODY_CODE, "/proposal/" + proposal.getId(), owner, u.getId());
        }
        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    @RequestMapping(value = "/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView decline(@PathVariable(value = "proposalId") int proposalId,
                                @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        User u = navigationUtility.getCurrentlyLoggedUser();
        Proposal proposal = proposalService.getWithRelatedEntities(proposalId);
        if (proposal == null || !userIsInvitedToProposal(u, proposal))
            return new ModelAndView("404").addObject("currentUser", u);
        User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
        proposal.getUsers().add(creator);
        long affectedRows = proposalService.setDeclineInvite(u.getId(), proposalId);
        if (affectedRows > 0){
            proposalService.delete(proposal, u);
            notificationUtility.sendNotifications(DECLINE_SUBJECT_CODE, DECLINE_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());
            //emailSender.sendEmailToUsers(DECLINE_SUBJECT, DECLINE_BODY, proposal.getUsers());
        }

        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    private boolean userIsInvitedToProposal(User user, Proposal proposal){
        for (User invitedUser: proposal.getUsers())
            if (invitedUser.getId() == user.getId())
                return true;
        return false;
    }

    private boolean userHasRepliedToProposal(User user, Proposal proposal){
        for (UserProposal userProp: proposal.getUserProposals()){
            if (userProp.getUser().getId() == user.getId() && userProp.getState() != UserProposalState.PENDING)
                return true;
        }
        return false;
    }
}
