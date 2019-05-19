package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.Utilities.UserUtility;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/proposal")
public class ProposalController {
    private final static String DELETE_SUBJECT= "Someone has declined the offer";
    private final static String DELETE_BODY = "Unfortunately, since someone has declined the proposal, this proposal has been deleted";

    @Autowired
    ProposalService proposalService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;

    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id) {
        final ModelAndView mav = new ModelAndView("proposal");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(id);
        Property property = propertyService.get(proposal.getPropertyId());
        if (proposal.getCreatorId() != u.getId() && !proposal.getUsers().contains(u) && property.getOwnerId() != u.getId())
            return new ModelAndView("404");
        mav.addObject("property", property);
        mav.addObject("proposal", proposal);
        mav.addObject("currentUser", u);
        return mav;
    }

    @RequestMapping(value = "/delete/{proposalId}", method = RequestMethod.POST )
    public ModelAndView delete(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Proposal proposal = proposalService.getById(proposalId);
        if (proposal.getCreatorId() != u.getId())
            return new ModelAndView("404");
        proposalService.delete(proposalId);

        Collection<User> retrieveUsers = proposal.getUsers();

        sendEmail(DELETE_SUBJECT, DELETE_BODY, retrieveUsers);
        return new ModelAndView("successfulDelete");
    }

    @RequestMapping(value = "/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView accept(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        return new ModelAndView("successfulDelete");
    }

    @RequestMapping(value = "/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView decline(@PathVariable(value = "proposalId") int proposalId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        return new ModelAndView("successfulDelete");
    }

    private void sendEmail(String title, String body, Collection<User> users) {
        String[] to = new String[users.size()];
        int index=0;
        for(User u : users){
            to[index++] = u.getEmail();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(body);
        emailSender.send(message);
    }
}
