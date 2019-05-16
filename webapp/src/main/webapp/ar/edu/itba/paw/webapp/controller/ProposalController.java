package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.Utilities.UserUtility;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    ProposalService proposalService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id) {
        final ModelAndView mav = new ModelAndView("proposal");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //TODO: Proposal view
        return mav;
    }

    @RequestMapping(value = "/create/{propertyId}", method = RequestMethod.POST )
    public ModelAndView create(@PathVariable(value = "propertyId") int propertyId, @Valid @ModelAttribute("proposalForm") ProposalForm form, final BindingResult errors) {
        if(errors.hasErrors()){
            return new ModelAndView("redirect:/" + propertyId);
        }

        String userEmail = UserUtility.getUsernameOfCurrentlyLoggedUser(SecurityContextHolder.getContext());
        long userId = userService.getByEmail(userEmail).getId();

        Either<Proposal, List<String>> proposalOrErrors = proposalService.createProposal(new Proposal.Builder()
                .withCreatorId(userId)
                .withPropertyId(propertyId)
                .withUsers(getUsersByIds(form.getInvitedUsersIds()))
                .build());

        if(proposalOrErrors.hasValue()){
            return new ModelAndView("redirect:/proposal/" + proposalOrErrors.value().getId());
        } else {
            System.out.println(proposalOrErrors.alternative());
            ModelAndView mav = new ModelAndView("redirect:/" + propertyId);
            mav.addObject("proposalFailed", true);
            return mav;
        }
    }

    private Collection<User> getUsersByIds(long[] ids){
        Collection<User> list = new LinkedList<>();
        if (ids != null && ids.length > 0)
            for (long i = 0; i < ids.length; i++){
                System.out.println(ids[(int)i]);
                list.add(userService.get(ids[(int)i]));
            }
        for (User user: list)
            System.out.println(user.getName());
        return list;
    }

}
