package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.model.exceptions.IllegalUserStateException;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.SignUpForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    private static final int STANDARD_PAGE_SIZE = 6;

    @Autowired
    private UserService userService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NavigationUtility navigationUtility;
    @Autowired
    private AuthenticationManager authenticationManager;



    @RequestMapping("/logIn")
    public ModelAndView login(HttpServletRequest request, @ModelAttribute FilteredSearchForm searchForm) {
//        HttpSession session = request.getSession(false);
//        SavedRequest savedRequest = (SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
//        if (savedRequest != null){
//            String targetUrl = savedRequest.getRedirectUrl();
//            System.out.println(targetUrl);
//        }
        return navigationUtility.mavWithNavigationAttributes("logInForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET )
    public ModelAndView signUp(HttpServletRequest request, @ModelAttribute("signUpForm") final SignUpForm form,
                               @ModelAttribute FilteredSearchForm searchForm) {

        return navigationUtility.mavWithNavigationAttributes("signUpForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST )
    public ModelAndView register(HttpServletRequest request,
                                 Locale loc,
                                 @RequestHeader String host,
                                 @Valid @ModelAttribute("signUpForm") SignUpForm form,
                                 final BindingResult errors,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        if(errors.hasErrors()){
            if (Role.valueOf(form.getRole()) != Role.ROLE_HOST)
                return signUp(request, form, searchForm);
            else if (!formHasValidHostInfo(errors.getFieldErrors()))
                return signUp(request, form, searchForm);
        }
        else if (!form.getRepeatPassword().equals(form.getPassword())){
            form.setRepeatPassword("");
            return signUp(request, form, searchForm).addObject("passwordMatch", false);
        }
        String viewName = "redirect:/";
        HttpSession session = request.getSession(false);
        SavedRequest savedRequest = (SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null)
            viewName = "redirect:"+ savedRequest.getRedirectUrl();

        try {
            Either<User, List<String>> maybeUser = userService.CreateUser(buildUserFromForm(form), loc, host + request.getContextPath());
            if(!maybeUser.hasValue()){
                form.setEmail("");
                logger.debug("NOT A UNIQUE EMAIL");
                return signUp(request, form, searchForm).addObject("uniqueEmail", false);
            }
            authenticateUserAndSetSession(form, request);
            return navigationUtility.mavWithNavigationAttributes(viewName);
        }
        catch(IllegalUserStateException e) {
            return new ModelAndView("redirect:/404");
        }
    }

    private void authenticateUserAndSetSession(SignUpForm form, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    private User buildUserFromForm(@ModelAttribute("signUpForm") @Valid SignUpForm form) {
        return new User.Builder()
                        .withEmail(form.getEmail())
                        .withGender(Gender.valueOf(form.getGender()))
                        .withName(form.getName())
                        .withLastName(form.getLastName())
                        .withPasswordHash(passwordEncoder.encode(form.getPassword()))
                        .withUniversityId(form.getUniversityId())
                        .withCareerId(form.getCareerId())
                        .withBio(form.getBio())
                        .withBirthDate(Date.valueOf(form.getBirthDate()))
                        .withContactNumber(form.getPhoneNumber())
                        .withRole(Role.valueOf(form.getRole()))
                        .build();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request, @ModelAttribute FilteredSearchForm searchForm, @PathVariable(value = "userId") long userId) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes();
        final User profileUser = userService.getWithRelatedEntities(userId);
        if (profileUser == null) {
            mav.setViewName("404");
            return mav;
        }
        mav.setViewName("profile");
        Collection<Proposal> proposals = proposalService.getAllProposalForUserId(profileUser.getId());

        String interestPage = request.getParameter("interestPage");
        String hostProposalPage = request.getParameter("hostProposalPage");
        String proposalPage = request.getParameter("proposalPage");
        String propertyPage = request.getParameter("propertyPage");

        PagedListHolder<Property> interestList = new PagedListHolder<>((List<Property>)profileUser.getInterestedProperties());
        PagedListHolder<Proposal> hostProposalList = new PagedListHolder<>((List<Proposal>)proposalService.getProposalsForOwnedProperties(profileUser));
        PagedListHolder<Proposal> proposalList = new PagedListHolder<>((List<Proposal>)proposalService.getAllProposalForUserId(profileUser.getId()));
        PagedListHolder<Property> hostPropertyList = new PagedListHolder<>((List<Property>)profileUser.getOwnedProperties());

        interestList.setPageSize(STANDARD_PAGE_SIZE);
        hostProposalList.setPageSize(STANDARD_PAGE_SIZE);
        proposalList.setPageSize(STANDARD_PAGE_SIZE);
        hostPropertyList.setPageSize(STANDARD_PAGE_SIZE);

        interestList.setPage(interestPage != null ? Integer.parseInt(interestPage) : 0);
        hostProposalList.setPage(hostProposalPage != null ? Integer.parseInt(hostProposalPage) : 0);
        proposalList.setPage(proposalPage != null ? Integer.parseInt(proposalPage) : 0);
        hostPropertyList.setPage(propertyPage != null ? Integer.parseInt(propertyPage) : 0);

        request.getSession().setAttribute("interests", interestList);
        request.getSession().setAttribute("hostProposals", hostProposalList);
        request.getSession().setAttribute("proposals", proposalList);
        request.getSession().setAttribute("properties", hostPropertyList);

        mav.addObject("profileUser", profileUser);

        if (proposals != null && proposals.size() != 0)
            mav.addObject("proposalPropertyNames", generatePropertyNames(proposals));
        return mav;
    }

    private Collection<String> generatePropertyNames(Collection<Proposal> list){
        List<String> result = new ArrayList<>();
        for (Proposal prop: list)
            if (prop != null)
                result.add(prop.getProperty().getDescription());
        return result;
    }

    private boolean formHasValidHostInfo(List<FieldError> errors){
        if (errors.size() > 2)
            return false;
        for (FieldError error: errors)
            if (!error.getField().equals("careerId") && !error.getField().equals("universityId"))
                return false;
        return true;
    }
}
