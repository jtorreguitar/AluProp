package ar.edu.itba.paw.webapp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.webapp.Utilities.UserUtility;
import ar.edu.itba.paw.webapp.form.PropertyCreationForm;
import ar.edu.itba.paw.webapp.form.SignUpForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class PropertyController {

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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("properties", propertyService.getAll());
        return mav;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id) {
        final ModelAndView mav = new ModelAndView("detailedProperty");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("property", propertyService.getPropertyWithRelatedEntities(id));
        if (!auth.getName().equals("anonymousUser"))
            mav.addObject("userInterested", userService.getUserIsInterestedInProperty(userService.getByEmail(auth.getName()).getId(), id));
        return mav;
    }

    @RequestMapping(value = "{id}/interest", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId) {
        String currentUsername = UserUtility.getUsernameOfCurrentlyLoggedUser(SecurityContextHolder.getContext());
        if (currentUsername.equals("anonymousUser")){
            ModelAndView mav = new ModelAndView("redirect:/" + propertyId);
            mav.addObject("noLogin", true);
            return mav;
        }
        final List<String> errorsOrLackThereof = propertyService.showInterestOrReturnErrors(propertyId, currentUsername);
        if(errorsOrLackThereof.isEmpty())
            return new ModelAndView("redirect:/" + propertyId);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("errors", errorsOrLackThereof);
        for (int i = 0; i < errorsOrLackThereof.size(); i++)
            System.out.println(errorsOrLackThereof.get(i));
        return mav;
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("propertyCreationForm") final PropertyCreationForm form, long[] imageArray) {
        if (imageArray != null && imageArray.length != 0)
            return ModelAndViewWithPropertyCreationAttributes(imageArray);
        return ModelAndViewWithPropertyCreationAttributes(new long[0]);
    }

    private ModelAndView create(Collection<String> errors) {
        ModelAndView mav = ModelAndViewWithPropertyCreationAttributes(null);
        mav.addObject("errors", errors);
        return mav;
    }

    private ModelAndView ModelAndViewWithPropertyCreationAttributes(long[] imageArray) {
        ModelAndView mav = new ModelAndView("createProperty");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        if (imageArray != null && imageArray.length != 0)
            mav.addObject("imagesAlreadyUploaded", imageArray);
        return mav;
    }

    @RequestMapping(value = "/host/create/uploadPictures", method = RequestMethod.POST)
    public ModelAndView uploadPictures(@RequestParam("file") MultipartFile[] files, @Valid @ModelAttribute PropertyCreationForm form, final BindingResult errors) {
        if (form.getImageIds() != null)
            if (errors.hasErrors())
                return create(form, form.getImageIds());
            else
                return create(form, errors, new long[0]);

        ArrayList<Long> images = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
            if (!files[i].isEmpty())
                images.add(imageService.create(files[i]));

        if (images.size() == 0)
            return create(form, new long[0]).addObject("noImages", true);

        long[] imageArray = new long[images.size()];
        for (int i = 0; i < images.size(); i++)
            imageArray[i] = images.get(i);
        form.setMainImageId(images.get(0));
        form.setImageIds(imageArray);
        return create(form, errors, imageArray).addObject("imagesUploaded", imageArray.length);
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute PropertyCreationForm propertyForm, final BindingResult errors, long[] imageArray) {

        if (errors.hasErrors())
            return create(propertyForm, imageArray);

        propertyForm.setMainImageId(propertyForm.getImageIds()[0]);
        Either<Property, Collection<String>> propertyOrErrors = propertyService.create(
                new Property.Builder()
                    .withCaption(propertyForm.getCaption())
                    .withDescription(propertyForm.getDescription())
                    .withNeighbourhoodId(propertyForm.getNeighbourhoodId())
                    .withPrice(propertyForm.getPrice())
                    .withPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()))
                    .withPrivacyLevel(propertyForm.getPrivacyLevel()>0)
                    .withCapacity(propertyForm.getCapacity())
                    .withMainImageId(propertyForm.getMainImageId())
                    .withServices(generateObjects(propertyForm.getServiceIds(), Service::new))
                    .withRules(generateObjects(propertyForm.getRuleIds(), Rule::new))
                    .withImages(generateObjects(propertyForm.getImageIds(), Image::new))
                    .withOwnerId(UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService).getId())
                    .build()
        );

        if(propertyOrErrors.hasValue())
            return new ModelAndView("redirect:/" + propertyOrErrors.value().getId());
        else
            return create(propertyOrErrors.alternative());
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
}
