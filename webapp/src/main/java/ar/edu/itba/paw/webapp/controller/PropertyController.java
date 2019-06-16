package ar.edu.itba.paw.webapp.controller;

import java.net.HttpURLConnection;
import java.util.*;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ProposalState;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.webapp.utilities.NotificationUtility;
import ar.edu.itba.paw.webapp.utilities.StatusCodeUtility;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
    private static final Integer MAX_SIZE = 12;

    private final static String INVITATION_SUBJECT_CODE= "notifications.proposals.invitation.subject";
    private final static String INVITATION_BODY_CODE = "notifications.proposals.invitation";

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NavigationUtility navigationUtility;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                              @ModelAttribute FilteredSearchForm searchForm,
                              @RequestParam(required = false, defaultValue = "12") int pageSize) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("index");
        PageResponse<Property> response = propertyService.getAll(new PageRequest(pageNumber, pageSize));
        navigationUtility.addPaginationAttributes(mav, response, MAX_SIZE);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@ModelAttribute("proposalForm") final ProposalForm form,
                            @ModelAttribute FilteredSearchForm searchForm,
                            @PathVariable("id") long id) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("detailedProperty");
        addObjectsToMavForDetailedProperty(id, mav);
        return mav;
    }

    private void addObjectsToMavForDetailedProperty(long propertyId, ModelAndView mav) {
        Property prop = propertyService.getPropertyWithRelatedEntities(propertyId);
        if(prop == null) {
            mav.setViewName("404");
            return;
        }
        User user = userService.getCurrentlyLoggedUser();
        mav.addObject("property", prop);
        if (user != null) {
            mav.addObject("userInterested", prop.getInterestedUsers().stream().anyMatch(u -> u.getId() == user.getId()));
            mav.addObject("interestedUsers", prop.getInterestedUsers());
        }
    }

    @RequestMapping(value = "/{id}/interest/", method = RequestMethod.GET)
    public ModelAndView interestGet(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        return navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
    }

    @RequestMapping(value = "/{id}/interest/", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
        User user = userService.getCurrentlyLoggedUser();
        if (user != null) {
            if (user.getRole().equals(Role.ROLE_GUEST)){
                final int code = propertyService.showInterestOrReturnErrors(propertyId, user);
                StatusCodeUtility.parseStatusCode(code, mav);
            }
        }
        else
            mav.addObject("noLogin", true);
        return mav;
    }


    @RequestMapping(value = "/{id}/deInterest", method = RequestMethod.POST)
    public ModelAndView deInterest(@PathVariable(value = "id") int propertyId,
                                   @ModelAttribute FilteredSearchForm searchForm) {
        User user = userService.getCurrentlyLoggedUser();
        ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
        final int code = propertyService.undoInterestOrReturnErrors(propertyId, user);
        StatusCodeUtility.parseStatusCode(code, mav);
        return mav;
    }

    @RequestMapping(value = "/interestsOfUser/{userId}")
    public ModelAndView interestsOfUser(@PathVariable(value = "userId") long userId) {
        return navigationUtility.mavWithNavigationAttributes("interestsOfUser")
                .addObject("interests", propertyService.getInterestsOfUser(userId));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                               @RequestParam(required = false, defaultValue = "9") int pageSize,
                               @Valid @ModelAttribute FilteredSearchForm searchForm,
                               final BindingResult errors,
                               Locale loc) {
        if(searchForm.getMinPrice() > searchForm.getMaxPrice()){
            String errorMsg = messageSource.getMessage("system.rangeError", null, loc);
            errors.addError(new FieldError("rangeError", "minPrice",errorMsg));
        }
        if (errors.hasErrors())
            return index(pageNumber,searchForm, pageSize);
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("index");
        mav.addObject("isSearch", true);
        PageResponse<Property> response = propertyService.advancedSearch(new PageRequest(pageNumber, pageSize), propertyForSearch(searchForm));
        navigationUtility.addPaginationAttributes(mav, response, MAX_SIZE);
        return mav;
    }

    private SearchableProperty propertyForSearch(FilteredSearchForm searchForm) {
        return new SearchableProperty.Builder()
            .withCapacity(searchForm.getCapacity())
            .withDescription(searchForm.getDescription())
            .withMaxPrice(searchForm.getMaxPrice())
            .withMinPrice(searchForm.getMinPrice())
            .withNeighbourhoodId(searchForm.getNeighbourhoodId())
            .withPrivacyLevel(searchForm.getPrivacyLevelAsEnum())
            .withPropertyType(searchForm.getPropertyTypeAsEnum())
            .withRuleIds(searchForm.getRuleIds())
            .withServiceIds(searchForm.getServiceIds())
            .build();
    }

    @RequestMapping(value = "/host/delete/{propertyId}", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithNavigationAttributes("successfulPropertyDelete");
        User u = userService.getCurrentlyLoggedUser();
        StatusCodeUtility.parseStatusCode(propertyService.delete(propertyId, u), mav);
        return mav;
    }

    @RequestMapping(value = "/host/changeStatus/{propertyId}", method = RequestMethod.POST)
    public ModelAndView changeStatus(HttpServletRequest request,
                                        @PathVariable(value = "propertyId") int propertyId,
                                        @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
        int statusCode = propertyService.changeStatus(propertyId);
        StatusCodeUtility.parseStatusCode(statusCode, mav);
        if(statusCode == HttpURLConnection.HTTP_OK)
            addObjectsToMavForDetailedProperty(propertyId, mav);
        return mav;
    }

    @RequestMapping(value = "/proposal/create/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithNavigationAttributes();
        Property prop = propertyService.get(propertyId);
        if (form.getInvitedUsersIds() == null || form.getInvitedUsersIds().length > prop.getCapacity() - 1)
            return get(form, searchForm, propertyId).addObject("maxPeople", prop.getCapacity()-1);

        long userId = userService.getCurrentlyLoggedUser().getId();

        Proposal.Builder builder = new Proposal.Builder()
                .withCreator(userService.get(userId))
                .withProperty(propertyService.get(propertyId));
        if(form.getInvitedUsersIds().length == 0)
            builder.withState(ProposalState.SENT);
        else
            builder.withState(ProposalState.PENDING);
        Proposal proposal = builder.build();
        Either<Proposal, List<String>> proposalOrErrors = proposalService.createProposal(proposal, form.getInvitedUsersIds());
        if(proposalOrErrors.hasValue()){
            notificationService.sendNotifications(INVITATION_SUBJECT_CODE, INVITATION_BODY_CODE, "/proposal/" + proposalOrErrors.value().getId(), proposalOrErrors.value().getUsers(), userId);
            mav.setViewName("redirect:/proposal/" + proposalOrErrors.value().getId());
            return mav;
        } else {
            mav.setViewName("redirect:/" + propertyId);
            mav.addObject("proposalFailed", true);
            return mav;
        }
    }
}
