package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.APJavaMailSender;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping("/proposal")
public class ProposalController {
//    private final static String DELETE_SUBJECT= "AluProp - A proposal has been dropped.";
//    private final static String DELETE_BODY = "Unfortunately, the creator of the proposal has dropped it, and it has been deleted.";
//
//    private final static String DECLINE_SUBJECT= "AluProp - A proposal has been dropped.";
//    private final static String DECLINE_BODY = "Unfortunately, since someone has declined the proposal, the proposal has been dropped";
//
//    private final static String SENT_SUBJECT= "AluProp - A proposal has been sent.";
//    private final static String SENT_BODY = "Every member in the proposal has accepted, so it has been sent to the property's owner.";
//
//    private final static String SENT_HOST_SUBJECT= "AluProp - There's a new proposal for your property!";

    private final static String DELETE_SUBJECT= "AluProp - Una propuesta se ha cancelado.";
    private final static String DELETE_BODY = "Lamentablemente, el creador de la propuesta la ha cancelado.\nSaludos,\nEl equipo de AluProp.";

    private final static String DECLINE_SUBJECT= "AluProp - Una propuesta se ha cancelado.";
    private final static String DECLINE_BODY = "Lamentablemente, alguien ha rechazado la propuesta y por lo tanto se ha cancelado.\nSaludos,\nEl equipo de AluProp.";

    private final static String SENT_SUBJECT= "AluProp - Una propuesta ha sido enviada!";
    private final static String SENT_BODY = "Todos los miembros de la propuesta han aceptado, así que fue enviada al dueño de la propiedad!\nSaludos,\nEl equipo de AluProp.";

    private final static String SENT_HOST_SUBJECT= "AluProp - Hay una propuesta nueva para tu propiedad!";


    @Autowired
    ProposalService proposalService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;

    @Autowired
    public APJavaMailSender emailSender;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id, @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("proposal");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(id);
        if (proposal == null)
            return new ModelAndView("404").addObject("currentUser", u);
        Property property = propertyService.get(proposal.getPropertyId());
        if (proposal.getCreatorId() != u.getId() && !userIsInvitedToProposal(u, proposal) && property.getOwnerId() != u.getId())
            return new ModelAndView("404").addObject("currentUser", u);
        if (userIsInvitedToProposal(u, proposal)){
            mav.addObject("isInvited", true);
            mav.addObject("hasReplied", userHasRepliedToProposal(u, proposal));
        }
        mav.addObject("property", property);
        mav.addObject("proposal", proposal);
        mav.addObject("currentUser", u);
        return mav;
    }

    @RequestMapping(value = "/delete/{proposalId}", method = RequestMethod.POST )
    public ModelAndView delete(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(proposalId);
        if (proposal == null || proposal.getCreatorId() != u.getId())
            return new ModelAndView("404").addObject("currentUser", u);
        proposalService.delete(proposalId);

        Collection<User> retrieveUsers = proposal.getUsers();

        emailSender.sendEmailToUsers(DELETE_SUBJECT, DELETE_BODY, retrieveUsers);
        return new ModelAndView("successfulDelete").addObject("currentUser", u);
    }

    @RequestMapping(value = "/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView accept(HttpServletRequest request, @PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(proposalId);
        if (!userIsInvitedToProposal(u, proposal))
            return new ModelAndView("404").addObject("currentUser", u);
        long affectedRows = proposalService.setAccept(u.getId(), proposalId);
        proposal = proposalService.getById(proposalId);
        if (proposal.isCompletelyAccepted()){
            User creator = userService.getWithRelatedEntities(proposal.getCreatorId());
            proposal.getUsers().add(creator);
            emailSender.sendEmailToUsers(SENT_SUBJECT, SENT_BODY, proposal.getUsers());
            Property property = propertyService.getPropertyWithRelatedEntities(proposal.getPropertyId());
            emailSender.sendEmailToSingleUser(SENT_HOST_SUBJECT, generateHostMailBody(proposal, property.getOwner(), request), property.getOwner());
        }
        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    @RequestMapping(value = "/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView decline(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(proposalId);
        if (proposal == null || !userIsInvitedToProposal(u, proposal))
            return new ModelAndView("404").addObject("currentUser", u);
        User creator = userService.getWithRelatedEntities(proposal.getCreatorId());
        proposal.getUsers().add(creator);
        long affectedRows = proposalService.setDecline(u.getId(), proposalId);
        if (affectedRows > 0){
            proposalService.delete(proposalId);
            emailSender.sendEmailToUsers(DECLINE_SUBJECT, DECLINE_BODY, proposal.getUsers());
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
        int userIndex = 0;
        for (int i = 0; i< proposal.getUsers().size(); i++){
            if (proposal.getUsers().get(i).getId() == user.getId()){
                userIndex = i;
                break;
            }
        }
        return proposal.getInvitedUserStates().get(userIndex) != 0;
    }

    private String generateHostMailBody(Proposal proposal, User host, HttpServletRequest request){
        Property property = propertyService.get(proposal.getPropertyId());
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
