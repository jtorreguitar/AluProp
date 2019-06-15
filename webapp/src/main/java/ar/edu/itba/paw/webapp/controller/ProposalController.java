package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.APJavaMailSender;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.utilities.StatusCodeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/proposal")
public class ProposalController {
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    private final static String DELETE_SUBJECT= "AluProp - Una propuesta se ha cancelado.";
    private final static String DELETE_BODY = "Lamentablemente, el creador de la propuesta la ha cancelado.\nSaludos,\nEl equipo de AluProp.";

    private final static String DECLINE_SUBJECT= "AluProp - Una propuesta se ha cancelado.";
    private final static String DECLINE_BODY = "Lamentablemente, alguien ha rechazado la propuesta y por lo tanto se ha cancelado.\nSaludos,\nEl equipo de AluProp.";

    private final static String SENT_SUBJECT= "AluProp - Una propuesta ha sido enviada!";
    private final static String SENT_BODY = "Todos los miembros de la propuesta han aceptado, así que fue enviada al dueño de la propiedad!\nSaludos,\nEl equipo de AluProp.";

    private final static String SENT_HOST_SUBJECT= "AluProp - Hay una propuesta nueva para tu propiedad!";


    private final static String DELETE_SUBJECT_CODE= "notifications.proposals.deleted.subject";
    private final static String DELETE_BODY_CODE = "notifications.proposals.deleted";

    private final static String DECLINE_SUBJECT_CODE= "notifications.proposals.declined.subject";
    private final static String DECLINE_BODY_CODE = "notifications.proposals.declined";

    private final static String SENT_SUBJECT_CODE= "notifications.proposals.sent.subject";
    private final static String SENT_BODY_CODE = "notifications.proposals.sent";

    private final static String SENT_HOST_SUBJECT_CODE= "notifications.proposals.hostProposal.subject";
    private final static String SENT_HOST_BODY_CODE= "notifications.proposals.hostProposal";

    @Autowired
    ProposalService proposalService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    UserService userService;
    @Autowired
    public APJavaMailSender emailSender;
    @Autowired
    protected NotificationService notificationService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private NeighbourhoodService neighbourhoodService;
    @Autowired
    private NavigationUtility navigationUtility;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id, @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        final User u = navigationUtility.getCurrentlyLoggedUser();
        final Proposal proposal = proposalService.getWithRelatedEntities(id);
        if (proposal == null) {
            mav.setViewName("404");
            return mav;
        }
        final Property property = propertyService.get(proposal.getProperty().getId());
        final User creator = userService.get(proposal.getCreator().getId());
        if (proposal.getCreator().getId() != u.getId() && !userIsInvitedToProposal(u, proposal) && property.getOwnerId() != u.getId())
            return new ModelAndView("404").addObject("currentUser", u);
        if (userIsInvitedToProposal(u, proposal)){
            mav.addObject("isInvited", true);
            mav.addObject("hasReplied", userHasRepliedToProposal(u, proposal));
        }
        mav.addObject("property", property);
        mav.addObject("proposal", proposal);
        mav.addObject("proposalUsers", proposal.getUsers());
        mav.addObject("creator", creator);
        mav.addObject("currentUser", u);
        mav.addObject("userStates", proposal.getUserStates());
        addSearchObjectsToMav(mav);
        addNotificationsToMav(mav, u);
        return mav;
    }

    @RequestMapping(value = "/user/delete/{proposalId}", method = RequestMethod.POST )
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
        sendNotifications(DELETE_SUBJECT_CODE, DELETE_BODY_CODE, "/proposal/" + proposal.getId(), retrieveUsers, u.getId());
        mav.setViewName("successfulDelete");
        return mav;
    }

    @RequestMapping(value = "/user/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView accept(HttpServletRequest request, @PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        User u = navigationUtility.getCurrentlyLoggedUser();
        Proposal proposal = proposalService.get(proposalId);
        if (!userIsInvitedToProposal(u, proposal))
            return new ModelAndView("403").addObject("currentUser", u);
        proposalService.setAccept(u.getId(), proposalId);
        proposal = proposalService.get(proposalId);
        if (proposal.isCompletelyAccepted()){
            User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
            proposal.getUsers().add(creator);
            sendNotifications(SENT_SUBJECT_CODE, SENT_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());

            Property property = propertyService.getPropertyWithRelatedEntities(proposal.getProperty().getId());
            List<User> owner = new ArrayList<>();
            owner.add(property.getOwner());
            sendNotifications(SENT_HOST_SUBJECT_CODE, SENT_HOST_BODY_CODE, "/proposal/" + proposal.getId(), owner, u.getId());
        }
        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    @RequestMapping(value = "/user/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView decline(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        User u = navigationUtility.getCurrentlyLoggedUser();
        Proposal proposal = proposalService.getWithRelatedEntities(proposalId);
        if (proposal == null || !userIsInvitedToProposal(u, proposal))
            return new ModelAndView("404").addObject("currentUser", u);
        User creator = userService.getWithRelatedEntities(proposal.getCreator().getId());
        proposal.getUsers().add(creator);
        long affectedRows = proposalService.setDecline(u.getId(), proposalId);
        if (affectedRows > 0){
            proposalService.delete(proposal, u);
            sendNotifications(DECLINE_SUBJECT_CODE, DECLINE_BODY_CODE, "/proposal/" + proposal.getId(), proposal.getUsers(), u.getId());
            //emailSender.sendEmailToUsers(DECLINE_SUBJECT, DECLINE_BODY, proposal.getUsers());
        }

        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    private void sendNotifications(String subjectCode, String textCode, String link, Collection<User> users, long currentUserId){
        for (User user: users){
            if (user.getId() == currentUserId)
                continue;
            Notification result = notificationService.createNotification(user.getId(), subjectCode, textCode, link);
            if (result == null)
                logger.error("Failed to deliver notification to user with id: " + user.getId());
        }
    }

    private boolean userIsInvitedToProposal(User user, Proposal proposal){
        for (User invitedUser: proposal.getUsers())
            if (invitedUser.getId() == user.getId())
                return true;
        return false;
    }

    private boolean userHasRepliedToProposal(User user, Proposal proposal){
        return proposal.getUserProposals().stream().anyMatch(up -> up.getUser().getId() == user.getId());
    }

    private String generateHostMailBody(Proposal proposal, User host, HttpServletRequest request){
        Property property = propertyService.get(proposal.getProperty().getId());
        StringBuilder builder = new StringBuilder("Hola ");
        builder.append(host.getName());
        builder.append("! ");
        builder.append("Los siguientes estudiantes están interesados en tu propiedad ");
        builder.append(property.getDescription());
        builder.append(": \n");
        for (User student: proposal.getUsers()){
            builder.append('•');
            builder.append(student.getFullName());
            builder.append(": ");
            builder.append(student.getEmail());
            builder.append(" | Teléfono:");
            builder.append(student.getContactNumber());
            builder.append('\n');
        }
        builder.append("Puedes contactarlos para completar la oferta!\n");
        builder.append("Puedes ver la propuesta usando el siguiente enlace: ");
        builder.append(generateProposalUrl(proposal, request));
        builder.append("\nSi no puedes ver la propuesta, recuerda iniciar sesión!\nSaludos,\nEl equipo de AluProp.");
        return  builder.toString();
    }

    private String generateProposalUrl(Proposal proposal, HttpServletRequest request){
        URI contextUrl = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath());
        return contextUrl.toString().split("/proposal")[0] + "/proposal/" + proposal.getId();
    }

    private void addSearchObjectsToMav(ModelAndView mav){
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
    }

    private void addNotificationsToMav(ModelAndView mav, User u){
        Collection<Notification> notifications = notificationService.getAllNotificationsForUser(u.getId(), new PageRequest(0, 5));
        mav.addObject("notifications", notifications);
    }

    //English text
//    private String generateHostMailBody(Proposal proposal, User host, HttpServletRequest request){
//        Property property = propertyService.get(proposal.getPropertyId());
//        StringBuilder builder = new StringBuilder("Hello ");
//        builder.append(host.getName());
//        builder.append("! ");
//        builder.append("The following students are interested in your property ");
//        builder.append(property.getDescription());
//        builder.append(": \n");
//        for (User student: proposal.getUsers()){
//            builder.append('•');
//            builder.append(student.getFullName());
//            builder.append(": ");
//            builder.append(student.getEmail());
//            builder.append('\n');
//        }
//        builder.append("You can now contact them to complete the offer!\n");
//        builder.append("The can be found on the following link: ");
//        builder.append(generateProposalUrl(proposal, request));
//        return  builder.toString();
//    }
}
