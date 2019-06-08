package ar.edu.itba.paw.webapp.controller;

import java.net.URI;
import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import ar.edu.itba.paw.interfaces.APJavaMailSender;
import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.model.exceptions.IllegalPropertyStateException;
import ar.edu.itba.paw.webapp.Utilities.StatusCodeUtility;
import ar.edu.itba.paw.webapp.Utilities.UserUtility;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.PropertyCreationForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
    private static final Integer MAX_SIZE = 9;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private NeighbourhoodService neighbourhoodService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    public APJavaMailSender emailSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                              @ModelAttribute FilteredSearchForm searchForm,
                              @RequestParam(required = false, defaultValue = "12") int pageSize) {
        final ModelAndView mav = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("currentUser", user);
        PageResponse<Property> response = propertyService.getAll(new PageRequest(pageNumber, pageSize));
        mav.addObject("properties", response.getResponseData());
        mav.addObject("currentPage", response.getPageNumber());
        mav.addObject("totalPages", response.getTotalPages());
        mav.addObject("totalElements", response.getTotalItems());
        mav.addObject("maxItems",MAX_SIZE);
        addSearchObjectsToMav(mav);
        return mav;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView get(@ModelAttribute("proposalForm") final ProposalForm form,
                            @ModelAttribute FilteredSearchForm searchForm,
                            @PathVariable("id") long id) {
        final ModelAndView mav = new ModelAndView("detailedProperty");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        Property prop = propertyService.getPropertyWithRelatedEntities(id);
        if (prop == null)
            return new ModelAndView("404").addObject("currentUser", user);
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("currentUser", user);
        mav.addObject("property", prop);

        if (user != null){
            mav.addObject("userInterested",userService.getUserIsInterestedInProperty(user.getId(), id));
            mav.addObject("interestedUsers", userService.getUsersInterestedInProperty(id, new PageRequest(0, 100)).getResponseData());
        }
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
        return mav;
    }

    @RequestMapping(value = "/{id}/interest/", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId,
                                 @ModelAttribute FilteredSearchForm searchForm) {
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        if (user != null){
            if (user.getRole().equals(Role.ROLE_GUEST)){
                final int code = propertyService.showInterestOrReturnErrors(propertyId, user);
                return StatusCodeUtility.parseStatusCode(code, "redirect:/" + propertyId);
            }
            return new ModelAndView("redirect:/" + propertyId);
        }
        return new ModelAndView("redirect:/" + propertyId).addObject("noLogin", true).addObject("currentUser", user);

    }


    @RequestMapping(value = "/{id}/deInterest", method = RequestMethod.POST)
    public ModelAndView deInterest(@PathVariable(value = "id") int propertyId,
                                   @ModelAttribute FilteredSearchForm searchForm) {
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        final int code = propertyService.undoInterestOrReturnErrors(propertyId, user);
        return StatusCodeUtility.parseStatusCode(code, "redirect:/" + propertyId);
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("propertyCreationForm") final PropertyCreationForm form,
                               @ModelAttribute FilteredSearchForm searchForm) {
        return ModelAndViewWithPropertyCreationAttributes();
    }

    private ModelAndView create(Collection<String> errors) {
        ModelAndView mav = ModelAndViewWithPropertyCreationAttributes();
        mav.addObject("errors", errors);

        return mav;
    }

    private ModelAndView ModelAndViewWithPropertyCreationAttributes() {
        ModelAndView mav = new ModelAndView("createProperty");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        mav.addObject("currentUser", user);
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("propertyTypes", new IdNamePair[]{new IdNamePair(0, "forms.house"),new IdNamePair(1, "forms.apartment"),new IdNamePair(2, "forms.loft")});
        mav.addObject("privacyLevels", new IdNamePair[]{new IdNamePair(0, "forms.privacy.individual"),new IdNamePair(1, "forms.privacy.shared")});
        addSearchObjectsToMav(mav);
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
            User u = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
            return new ModelAndView("404").addObject("currentUser", u);
        }
    }

    private Property buildPropertyForCreation(@ModelAttribute @Valid PropertyCreationForm propertyForm) {
        return new Property.Builder()
            .withCaption(propertyForm.getCaption())
            .withDescription(propertyForm.getDescription())
            .withNeighbourhoodId(propertyForm.getNeighbourhoodId())
            .withPrice(propertyForm.getPrice())
            .withPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()))
            .withPrivacyLevel(propertyForm.getPrivacyLevel() > 0)
            .withCapacity(propertyForm.getCapacity())
            .withMainImageId(propertyForm.getMainImageId())
            .withServices(generateObjects(propertyForm.getServiceIds(), Service::new))
            .withRules(generateObjects(propertyForm.getRuleIds(), Rule::new))
            .withImages(generateObjects(propertyForm.getImageIds(), Image::new))
            .withOwnerId(UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService).getId())
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
        return new ModelAndView("interestsOfUser")
                .addObject("interests", propertyService.getInterestsOfUser(userId));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                               @RequestParam(required = false, defaultValue = "9") int pageSize,
                               @Valid @ModelAttribute FilteredSearchForm searchForm,
                               final BindingResult errors,
                               Locale loc){
        if(searchForm.getMinPrice() > searchForm.getMaxPrice()){
            String errorMsg = messageSource.getMessage("system.rangeError", null, loc);
            errors.addError(new FieldError("rangeError", "minPrice",errorMsg));
        }
        if (errors.hasErrors()){
            return index(pageNumber,searchForm,pageSize);
        }
        final ModelAndView mav = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        mav.addObject("currentUser", user);
        mav.addObject("userRole", auth.getAuthorities());
        PageResponse<Property> response = propertyService.advancedSearch(new PageRequest(pageNumber, pageSize),searchForm.getDescription(), searchForm.getPropertyType(), searchForm.getNeighbourhoodId(), searchForm.getPrivacyLevel(), searchForm.getCapacity(), searchForm.getMinPrice(), searchForm.getMaxPrice(), searchForm.getRuleIds(), searchForm.getServiceIds());
        mav.addObject("properties", response.getResponseData());
        mav.addObject("currentPage", response.getPageNumber());
        mav.addObject("totalPages", response.getTotalPages());
        mav.addObject("totalElements", response.getTotalItems());
        mav.addObject("maxItems",MAX_SIZE);
        mav.addObject("isSearch", true);
        mav.addObject("privacyLevels", new IdNamePair[]{new IdNamePair(0, "forms.privacy.individual"),new IdNamePair(1, "forms.privacy.shared")});
        addSearchObjectsToMav(mav);
        return mav;
    }

    @RequestMapping(value = "/property/delete/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId, @ModelAttribute FilteredSearchForm searchForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser"))
            return new ModelAndView("404");
        User u = userService.getUserWithRelatedEntitiesByEmail(auth.getName());
        Property prop = propertyService.get(propertyId);
        if (prop.getOwnerId() != u.getId())
            return new ModelAndView("404").addObject("currentUser", u);
        propertyService.delete(propertyId);
        return new ModelAndView("successfulPropertyDelete").addObject("currentUser", u);
    }

    @RequestMapping(value = "/proposal/create/{propertyId}", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                               @PathVariable(value = "propertyId") int propertyId,
                               @Valid @ModelAttribute("proposalForm") ProposalForm form,
                               final BindingResult errors,
                               @ModelAttribute FilteredSearchForm searchForm) {
        Property prop = propertyService.get(propertyId);
        if (form.getInvitedUsersIds().length  < 1 || form.getInvitedUsersIds() == null || form.getInvitedUsersIds().length > prop.getCapacity() - 1)
            return get(form, searchForm, propertyId).addObject("maxPeople", prop.getCapacity()-1);

        String userEmail = UserUtility.getUsernameOfCurrentlyLoggedUser(SecurityContextHolder.getContext());
        long userId = userService.getByEmail(userEmail).getId();

        Proposal proposal = new Proposal.Builder()
                .withCreatorId(userId)
                .withPropertyId(propertyId)
                .withUsers(getUsersByIds(form.getInvitedUsersIds()))
                .withInvitedUserStates(new ArrayList<>())
                .build();
        for (Long id: form.getInvitedUsersIds())
            proposal.getInvitedUserStates().add(0);
        Either<Proposal, List<String>> proposalOrErrors = proposalService.createProposal(proposal);
        if(proposalOrErrors.hasValue()){
//            emailSender.sendEmailToUsers("AluProp - You have been invited to a proposal!",
//                    "You can reply to the proposal using the following link: \n" +
//                            generateProposalUrl(proposalOrErrors.value(), request) +
//                            "\nIf you can't see the proposal, remember to log in!\n Cheers!",
//                    proposalOrErrors.value().getUsers());
            emailSender.sendEmailToUsers("AluProp - Te han invitado a una propuesta!",
                    "Puedes responder a la propuesta usando el siguiente enlace: \n" +
                            generateProposalUrl(proposalOrErrors.value(), request) +
                            "\nSi no puedes ver la propuesta, recuerda iniciar sesión!\nSaludos,\nEl equipo de AluProp.",
                    proposalOrErrors.value().getUsers());
            return new ModelAndView("redirect:/proposal/" + proposalOrErrors.value().getId());
        } else {
            ModelAndView mav = new ModelAndView("redirect:/" + propertyId);
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

    private void addSearchObjectsToMav(ModelAndView mav){
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
    }
}
