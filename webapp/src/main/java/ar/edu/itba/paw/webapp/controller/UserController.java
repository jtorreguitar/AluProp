package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.APJavaMailSender;
import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.model.exceptions.IllegalUserStateException;
import ar.edu.itba.paw.webapp.utilities.UserUtility;
import ar.edu.itba.paw.webapp.form.SignUpForm;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public APJavaMailSender emailSender;
    @Autowired
    public NeighbourhoodService neighbourhoodService;
    @Autowired
    public RuleService ruleService;
    @Autowired
    public ServiceService serviceService;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    protected NotificationService notificationService;

    @RequestMapping("/logIn")
    public ModelAndView login(HttpServletRequest request, @ModelAttribute FilteredSearchForm searchForm) {
        HttpSession session = request.getSession(false);
        SavedRequest savedRequest = (SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println(targetUrl);
        }
        return new ModelAndView("logInForm");
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET )
    public ModelAndView signUp(HttpServletRequest request, @ModelAttribute("signUpForm") final SignUpForm form,
                               @ModelAttribute FilteredSearchForm searchForm) {
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        ModelAndView mav = new ModelAndView("signUpForm");

        mav.addObject("currentUser", user);
        mav.addObject("universities", universityService.getAll());
        mav.addObject("careers", careerService.getAll());

        addSearchObjectsToMav(mav);

        return mav;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST )
    public ModelAndView register(HttpServletRequest request, @Valid @ModelAttribute("signUpForm") SignUpForm form,
                                 final BindingResult errors,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        if(errors.hasErrors()){
            return signUp(request, form, searchForm);
        }
        else if (!form.getRepeatPassword().equals(form.getPassword())){
            form.setRepeatPassword("");
            return signUp(request, form, searchForm).addObject("passwordMatch", false);
        }
        ModelAndView mav = new ModelAndView("redirect:/");
        HttpSession session = request.getSession(false);
        SavedRequest savedRequest = (SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null)
            mav = new ModelAndView("redirect:"+ savedRequest.getRedirectUrl());

        try {
            Either<User, List<String>> maybeUser = userService.CreateUser(buildUserFromForm(form));
            if(!maybeUser.hasValue()){
                form.setEmail("");
                logger.debug("NOT A UNIQUE EMAIL");
                return signUp(request, form, searchForm).addObject("uniqueEmail", false);
            }
            User user = maybeUser.value();
            String title = redactConfirmationTitle(user);
            String body = redactConfirmationBody();
            emailSender.sendEmailToSingleUser(title, body, user);
            logger.debug("Confirmation email sent to: " + user.getEmail());
            authenticateUserAndSetSession(form, request);
            return mav;
        }
        catch(IllegalUserStateException e) {
            return new ModelAndView("404");
        }
    }

    private void authenticateUserAndSetSession(SignUpForm form, HttpServletRequest request) {
        String username = form.getEmail();
        String password = form.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    private String redactConfirmationTitle(User user) {
        return "Hola " + user.getName() + ", bienvenido a AluProp!";
    }

    private String redactConfirmationBody() {
        return "Muchas gracias por elegirnos!\n Ingresa a http://pawserver.it.itba.edu.ar/paw-2019a-5/ para ver las propiedades disponibles, mostrar tu interés y crear propuestas, o bien publicar tus propiedades!\nSaludos,\nEl equipo de AluProp.";
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
    public ModelAndView profile(@ModelAttribute FilteredSearchForm searchForm, @PathVariable(value = "userId") long userId) {
        String email = UserUtility.getUsernameOfCurrentlyLoggedUser(SecurityContextHolder.getContext());
        User currentUser = userService.getUserWithRelatedEntitiesByEmail(email);
        User profileUser = userService.get(userId);
        if (profileUser == null)
            return new ModelAndView("404").addObject("currentUser", currentUser);
        ModelAndView mav = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Proposal> proposals = (List<Proposal>) proposalService.getAllProposalForUserId(profileUser.getId());
        List<Property> properties = (List<Property>) propertyService.getByOwnerId(profileUser.getId());
        mav.addObject("currentUser", currentUser);
        mav.addObject("profileUser", profileUser);
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("interests", propertyService.getInterestsOfUser(profileUser.getId()));
        mav.addObject("proposals", proposals);
        addNotificationsToMav(mav, profileUser);
        addSearchObjectsToMav(mav);
        mav.addObject("properties", properties);
        if (proposals != null)
            mav.addObject("proposalPropertyNames", generatePropertyNames(proposals));

        return mav;
    }

    @RequestMapping(value = "/interested/{propertyId}")
    public ModelAndView interested(@PathVariable long propertyId,
                                   @RequestParam("pageNumber") int pageNumber,
                                   @RequestParam("pageSize") int pageSize) {
        return new ModelAndView("interested")
                        .addObject("users",
                                    userService.getUsersInterestedInProperty(propertyId, new PageRequest(pageNumber, pageSize)));
    }

    private List<String> generatePropertyNames(List<Proposal> list){
        List<String> result = new ArrayList<>();
        for (Proposal prop: list)
            result.add(propertyService.get(prop.getProperty().getId()).getDescription());
        return result;
    }

    private void addSearchObjectsToMav(ModelAndView mav){
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
    }

    private void addNotificationsToMav(ModelAndView mav, User u){
        List<Notification> notifications = notificationService.getAllNotificationsForUser(u.getId(), new PageRequest(0, 5));
        mav.addObject("notifications", notifications);
    }
}
