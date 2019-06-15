package ar.edu.itba.paw.webapp.controller;

import java.net.URI;
import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.enums.ProposalState;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.model.exceptions.IllegalPropertyStateException;
import ar.edu.itba.paw.webapp.utilities.StatusCodeUtility;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.PropertyCreationForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
    private static final Integer MAX_SIZE = 9;

    private final static String INVITATION_SUBJECT_CODE= "notifications.proposals.invitation.subject";
    private final static String INVITATION_BODY_CODE = "notifications.proposals.invitation";

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NavigationUtility navigationUtility;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                              @ModelAttribute FilteredSearchForm searchForm,
                              @RequestParam(required = false, defaultValue = "12") int pageSize) {
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes("index");
        PageResponse<Property> response = propertyService.getAll(new PageRequest(pageNumber, pageSize));
        navigationUtility.addPaginationAttributes(mav, response, MAX_SIZE);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@ModelAttribute("proposalForm") final ProposalForm form,
                            @ModelAttribute FilteredSearchForm searchForm,
                            @PathVariable("id") long id) {
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        final Property prop = propertyService.getPropertyWithRelatedEntities(id);
        addObjectsToMavForDetailedProperty(prop, mav);
        mav.setViewName("detailedProperty");
        return mav;
    }

    private void addObjectsToMavForDetailedProperty(Property prop, ModelAndView mav) {
        if(prop == null) {
            mav.setViewName("404");
            return;
        }
        User user = navigationUtility.getCurrentlyLoggedUser();
        mav.addObject("property", prop);
        if (user != null) {
            mav.addObject("userInterested",userService.getUserIsInterestedInProperty(user.getId(), prop.getId()));
            mav.addObject("interestedUsers", userService.getUsersInterestedInProperty(prop.getId(), new PageRequest(0, 100)).getResponseData());
        }
    }

    @RequestMapping(value = "/{id}/interest/", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        User user = navigationUtility.getCurrentlyLoggedUser();
        if (user != null){
            if (user.getRole().equals(Role.ROLE_GUEST)){
                final int code = propertyService.showInterestOrReturnErrors(propertyId, user);
                mav.setViewName("redirect:/" + propertyId);
                StatusCodeUtility.parseStatusCode(code, mav);
            }
            else
                mav.setViewName("redirect:/" + propertyId);
        }
        else {
            mav.setViewName("redirect:/" + propertyId);
            mav.addObject("noLogin", true)
                .addObject("currentUser", user);
        }
        return mav;
    }


    @RequestMapping(value = "/{id}/deInterest", method = RequestMethod.POST)
    public ModelAndView deInterest(@PathVariable(value = "id") int propertyId,
                                   @ModelAttribute FilteredSearchForm searchForm) {
        User user = navigationUtility.getCurrentlyLoggedUser();
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes("redirect:/" + propertyId);
        final int code = propertyService.undoInterestOrReturnErrors(propertyId, user);
        StatusCodeUtility.parseStatusCode(code, mav);
        return mav;
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("propertyCreationForm") final PropertyCreationForm form,
                               @ModelAttribute("filteredSearchForm") FilteredSearchForm searchForm) {
        return navigationUtility.mavWithGeneralNavigationAttributes("createProperty");
    }

    private ModelAndView create(Collection<String> errors) {
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes("createProperty");
        mav.addObject("errors", errors);
        return mav;
    }

    private long[] loadImagesToDatabase(MultipartFile[] files){
        ArrayList<Long> images = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
            if (!files[i].isEmpty())
                images.add(imageService.create(files[i]));

        long[] imageArray = new long[images.size()];
        for (int i = 0; i < images.size(); i++)
            imageArray[i] = images.get(i);
        return imageArray;
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("file") MultipartFile[] files,
                               @Valid @ModelAttribute PropertyCreationForm propertyForm,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        int numFiles = 0;
        for (int i = 0; i < files.length; i++)
            if (!files[i].isEmpty()) numFiles++;
        if (numFiles == 0)
            return create(propertyForm, searchForm).addObject("noImages", true);
        if (errors.hasErrors())
            return create(propertyForm, searchForm);
        long[] uploadedFiles = loadImagesToDatabase(files);
        propertyForm.setMainImageId(uploadedFiles[0]);
        propertyForm.setImageIds(uploadedFiles);
        try {
            Either<Property, Collection<String>> propertyOrErrors = propertyService.create(buildPropertyForCreation(propertyForm));
            if(propertyOrErrors.hasValue())
                return new ModelAndView("redirect:/" + propertyOrErrors.value().getId());
            else
                return create(propertyOrErrors.alternative());
        }
        catch(IllegalPropertyStateException e) {
            return navigationUtility.mavWithGeneralNavigationAttributes("404");
        }
    }

    private Property buildPropertyForCreation(@ModelAttribute @Valid PropertyCreationForm propertyForm) {
        return new Property.Builder()
            .withCaption(propertyForm.getCaption())
            .withDescription(propertyForm.getDescription())
            .withNeighbourhood(new Neighbourhood(propertyForm.getNeighbourhoodId()))
            .withPrice(propertyForm.getPrice())
            .withPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()))
            .withPrivacyLevel(propertyForm.getPrivacyLevel() > 0)
            .withCapacity(propertyForm.getCapacity())
            .withMainImage(new Image(propertyForm.getMainImageId()))
            .withServices(generateObjects(propertyForm.getServiceIds(), Service::new))
            .withRules(generateObjects(propertyForm.getRuleIds(), Rule::new))
            .withImages(generateObjects(propertyForm.getImageIds(), Image::new))
            .withOwner(navigationUtility.getCurrentlyLoggedUser())
            .withAvailability(Availability.valueOf("AVAILABLE"))
            .build();
    }

    private <T> Collection<T> generateObjects(long[] objectIds, LongFunction<T> function) {
        if(objectIds != null && objectIds.length > 0)
            return Arrays.stream(objectIds).mapToObj(function).collect(Collectors.toList());
        else
            return new LinkedList<>();
    }

    @RequestMapping(value = "/interestsOfUser/{userId}")
    public ModelAndView interestsOfUser(@PathVariable(value = "userId") long userId) {
        return navigationUtility.mavWithGeneralNavigationAttributes("interestsOfUser")
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
            return index(pageNumber,searchForm,pageSize);
        final ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes("index");
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

    @RequestMapping(value = "/property/delete/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @ModelAttribute FilteredSearchForm searchForm) {
        User u = navigationUtility.getCurrentlyLoggedUser();
        Property prop = propertyService.get(propertyId);
        if (prop == null || prop.getOwner().getId() != u.getId())
            return navigationUtility.mavWithGeneralNavigationAttributes("404");
        propertyService.delete(propertyId);
        return navigationUtility.mavWithGeneralNavigationAttributes("successfulPropertyDelete");
    }

    @RequestMapping(value = "/host/property/changeStatus/{propertyId}", method = RequestMethod.POST)
    public ModelAndView changeStatus(HttpServletRequest request,
                                        @PathVariable(value = "propertyId") int propertyId,
                                        @ModelAttribute FilteredSearchForm searchForm) {
        User u = navigationUtility.getCurrentlyLoggedUser();
        Property prop = propertyService.get(propertyId);
        if (prop == null || prop.getOwner().getId() != u.getId())
            return navigationUtility.mavWithGeneralNavigationAttributes("404");

        propertyService.changeStatus(prop,propertyId);
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        addObjectsToMavForDetailedProperty(prop, mav);
        return mav;
    }

    @RequestMapping(value = "/proposal/create/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes();
        Property prop = propertyService.get(propertyId);
        if (form.getInvitedUsersIds() == null || form.getInvitedUsersIds().length > prop.getCapacity() - 1)
            return get(form, searchForm, propertyId).addObject("maxPeople", prop.getCapacity()-1);

        long userId = navigationUtility.getCurrentlyLoggedUser().getId();

        Proposal proposal = new Proposal.Builder()
                .withCreator(userService.get(userId))
                .withProperty(propertyService.get(propertyId))
                .withState(ProposalState.PENDING)
//                .withUserProposals(getUsersByIds(form.getInvitedUsersIds()))
                .build();
        Either<Proposal, List<String>> proposalOrErrors = proposalService.createProposal(proposal, form.getInvitedUsersIds());
        if(proposalOrErrors.hasValue()){
//            emailSender.sendEmailToUsers("AluProp - You have been invited to a proposal!",
//                    "You can reply to the proposal using the following link: \n" +
//                            generateProposalUrl(proposalOrErrors.value(), request) +
//                            "\nIf you can't see the proposal, remember to log in!\n Cheers!",
//                    proposalOrErrors.value().getUsers());
//            emailSender.sendEmailToUsers("AluProp - Te han invitado a una propuesta!",
//                    "Puedes responder a la propuesta usando el siguiente enlace: \n" +
//                            generateProposalUrl(proposalOrErrors.value(), request) +
//                            "\nSi no puedes ver la propuesta, recuerda iniciar sesión!\nSaludos,\nEl equipo de AluProp.",
//                    proposalOrErrors.value().getUsers());
            sendNotifications(INVITATION_SUBJECT_CODE, INVITATION_BODY_CODE, "/proposal/" + proposalOrErrors.value().getId(), proposalOrErrors.value().getUsers(), userId);
            mav.setViewName("redirect:/proposal/" + proposalOrErrors.value().getId());
            return mav;
        } else {
            mav.setViewName("redirect:/" + propertyId);
            mav.addObject("proposalFailed", true);
            return mav;
        }
    }

    private List<User> getUsersByIds(long[] ids){
        List<User> list = new LinkedList<>();
        if (ids != null && ids.length > 0)
            for (long i = 0; i < ids.length; i++){
                list.add(userService.get(ids[(int)i]));
            }
        return list;
    }

    private String generateProposalUrl(Proposal proposal, HttpServletRequest request){
        URI contextUrl = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath());
        return contextUrl.toString().split("/proposal")[0] + "/proposal/" + proposal.getId();
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
}
