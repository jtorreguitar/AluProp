package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserProposal;
import ar.edu.itba.paw.model.enums.UserProposalState;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@RequestMapping("/proposal")
public class ProposalController {
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;
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
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes();
        final User u = userService.getCurrentlyLoggedUser();
        final Proposal proposal = proposalService.getWithRelatedEntities(id);
        if (proposal == null) {
            mav.setViewName("404");
            return mav;
        }
        final Property property = propertyService.get(proposal.getProperty().getId());
        final User creator = userService.get(proposal.getCreator().getId());
        if (proposal.getCreator().getId() != u.getId() && !userIsInvitedToProposal(u, proposal) && property.getOwner().getId() != u.getId())
            return new ModelAndView("404").addObject("currentUser", u);
        if (userIsInvitedToProposal(u, proposal)){
            mav.addObject("isInvited", true);
            mav.addObject("hasReplied", userHasRepliedToProposal(u, proposal));
        }


        mav.addObject("property", property);
        mav.addObject("proposal", proposal);
        mav.addObject("proposalUsers", proposal.getUsersWithoutCreator(creator.getId()));
        mav.addObject("creator", creator);
        mav.addObject("currentUser", u);
        mav.addObject("userStates", proposal.getUserStates(creator.getId()));
        addSearchObjectsToMav(mav);
        mav.setViewName("proposal");
        return mav;
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
