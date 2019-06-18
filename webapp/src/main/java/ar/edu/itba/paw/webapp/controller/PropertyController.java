package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.SearchableProperty;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyOrder;
import ar.edu.itba.paw.model.enums.ProposalState;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.utilities.StatusCodeUtility;
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
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

@Controller
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProposalService proposalService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NavigationUtility navigationUtility;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int pageNumber,
                              @ModelAttribute FilteredSearchForm searchForm,
                              @RequestParam(required = false, defaultValue = "12") int pageSize) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("index");
        String propertyOrder = request.getParameter("orderBy")==null?"NEWEST":request.getParameter("orderBy");
        PageResponse<Property> response = propertyService.getAll(new PageRequest(pageNumber, pageSize), PropertyOrder.valueOf(propertyOrder));
        navigationUtility.addPaginationAttributes(mav, response);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@ModelAttribute("proposalForm") final ProposalForm form,
                            @ModelAttribute FilteredSearchForm searchForm,
                            @PathVariable("id") long id) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("detailedProperty");
        final Property property = propertyService.getPropertyWithRelatedEntities(id);
        final int statusCode = propertyService.propertyCanBeShown(property);
        StatusCodeUtility.parseStatusCode(statusCode, mav);
        addObjectsToMavForDetailedProperty(statusCode, mav, property);
        mav.addObject("property", property);
        return mav;
    }

    private void addObjectsToMavForDetailedProperty(int statusCode, ModelAndView mav, Property property) {
        final User user = userService.getCurrentlyLoggedUser();
        if (statusCode == HttpURLConnection.HTTP_OK && user != null && property != null) {
            mav.addObject("userInterested", property.getInterestedUsers().stream().anyMatch(u -> u.getId() == user.getId()));
            mav.addObject("interestedUsers", property.getInterestedUsers());
        }
    }

    @RequestMapping(value = "/{id}/interest/", method = RequestMethod.GET)
    public ModelAndView interestGet(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        return navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
    }

    @RequestMapping(value = "/{id}/guest/interest/", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        final User user = userService.getCurrentlyLoggedUser();
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
        final int code = propertyService.showInterestOrReturnErrors(propertyId, user);
        StatusCodeUtility.parseStatusCode(code, mav);
        return mav;
    }

    @RequestMapping(value = "/{id}/deInterest", method = RequestMethod.POST)
    public ModelAndView deInterest(@PathVariable(value = "id") int propertyId,
                                   @ModelAttribute FilteredSearchForm searchForm) {
        final User user = userService.getCurrentlyLoggedUser();
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
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
    public ModelAndView search(HttpServletRequest request,@RequestParam(required = false, defaultValue = "0") int pageNumber,
                               @RequestParam(required = false, defaultValue = "12") int pageSize,
                               @Valid @ModelAttribute FilteredSearchForm searchForm,
                               final BindingResult errors,
                               Locale loc) {
        String propertyOrder = request.getParameter("orderBy")==null?"NEWEST":request.getParameter("orderBy");
        if(searchForm.getMinPrice() > searchForm.getMaxPrice()){
            String errorMsg = messageSource.getMessage("system.rangeError", null, loc);
            errors.addError(new FieldError("rangeError", "minPrice",errorMsg));
        }
        if (errors.hasErrors())
            return index(request,pageNumber,searchForm, pageSize);
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("index");
        mav.addObject("isSearch", true);
        PageResponse<Property> response = propertyService.advancedSearch(new PageRequest(pageNumber, pageSize), propertyForSearch(searchForm, PropertyOrder.valueOf(propertyOrder)));
        navigationUtility.addPaginationAttributes(mav, response);
        return mav;
    }

    private SearchableProperty propertyForSearch(FilteredSearchForm searchForm, PropertyOrder propertyOrder) {
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
            .withPropertyOrder(propertyOrder)
            .build();
    }

    @RequestMapping(value = "/host/delete/{propertyId}", method = RequestMethod.POST)
    public ModelAndView delete(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("successfulPropertyDelete");
        final User u = userService.getCurrentlyLoggedUser();
        StatusCodeUtility.parseStatusCode(propertyService.delete(propertyId, u), mav);
        return mav;
    }

    @RequestMapping(value = "/host/changeStatus/{propertyId}", method = RequestMethod.POST)
    public ModelAndView changeStatus(HttpServletRequest request,
                                        @PathVariable(value = "propertyId") int propertyId,
                                        @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes("redirect:/" + propertyId);
        final int statusCode = propertyService.changeStatus(propertyId);
        StatusCodeUtility.parseStatusCode(statusCode, mav);
        if(statusCode == HttpURLConnection.HTTP_OK)
            mav.addObject("interestedUsers", propertyService.get(propertyId).getInterestedUsers());
        return mav;
    }

    @RequestMapping(value = "/proposal/create/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = navigationUtility.mavWithNavigationAttributes();
        final Property prop = propertyService.get(propertyId);
        if (form.getInvitedUsersIds() != null && form.getInvitedUsersIds().length > prop.getCapacity() - 1)
            return get(form, searchForm, propertyId).addObject("maxPeople", prop.getCapacity()-1);
        if (prop.getAvailability() == Availability.RENTED){
            mav.setViewName("redirect:/" + propertyId);
            return mav;
        }

        long userId = userService.getCurrentlyLoggedUser().getId();

        Proposal.Builder builder = new Proposal.Builder()
                .withCreator(userService.get(userId))
                .withProperty(propertyService.get(propertyId));

        if(form.getInvitedUsersIds() == null || form.getInvitedUsersIds().length == 0)
            builder.withState(ProposalState.SENT);
        else
            builder.withState(ProposalState.PENDING);
        final Proposal proposal = builder.build();

        final long duplicateId = proposalService.findDuplicateProposal(proposal, form.getInvitedUsersIds());
        if(duplicateId != -1) {
            mav.setViewName("redirect:/proposal/" + duplicateId);
            return mav;
        }

        Either<Proposal, List<String>> proposalOrErrors = proposalService.createProposal(proposal, form.getInvitedUsersIds());

        if(proposalOrErrors.hasValue()){
            mav.setViewName("redirect:/proposal/" + proposalOrErrors.value().getId());
            return mav;
        } else {
            mav.setViewName("redirect:/" + propertyId);
            mav.addObject("errors", proposalOrErrors.alternative());
            return mav;
        }
    }
}
