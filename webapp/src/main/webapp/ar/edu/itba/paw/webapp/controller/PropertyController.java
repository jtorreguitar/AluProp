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
import org.springframework.validation.BindingResult;
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
        return mav;
    }

    @RequestMapping(value = "{id}/interest", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable(value = "id") int propertyId) {
        String currentUsername = UserUtility.getUsernameOfCurrentlyLoggedUser(SecurityContextHolder.getContext());
        final List<String> errorsOrLackThereof = propertyService.showInterestOrReturnErrors(propertyId, currentUsername);
        if(errorsOrLackThereof.isEmpty())
            return new ModelAndView("redirect:/" + propertyId);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("errors", errorsOrLackThereof);
        return mav;
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("propertyCreationForm") final PropertyCreationForm form) {
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
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        return mav;
    }

    @RequestMapping(value = "/host/create/uploadPictures", method = RequestMethod.POST)
    public @ResponseBody ModelAndView uploadPictures(@RequestParam("file") MultipartFile[] files, @ModelAttribute PropertyCreationForm form, final BindingResult errors) {
        ArrayList<Long> images = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            if (!files[i].isEmpty()){
                images.add(imageService.create(files[i]));
            }
        }
        long[] imageArray = new long[images.size()];
        form.setMainImageId(images.get(0));
        for (int i = 0; i < images.size(); i++){
            imageArray[i] = images.get(i);
        }
        form.setImageIds(imageArray);
        System.out.println(Arrays.toString(imageArray));
        System.out.println(form.getCaption());
        return create(form, errors);
    }

    @RequestMapping(value = "/host/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute PropertyCreationForm propertyForm, final BindingResult errors) {
        if (errors.hasErrors()){
            System.out.println("dsdsds");
            return create(propertyForm);
        }
        System.out.println("In create: " + Arrays.toString(propertyForm.getImageIds()));
        Either<Property, Collection<String>> propertyOrErrors = propertyService.create(
                new Property.Builder()
                    .withCaption(propertyForm.getCaption())
                    .withDescription(propertyForm.getDescription())
                    .withNeighbourhoodId(propertyForm.getNeighbourhoodId())
                    .withPrice(propertyForm.getPrice())
                    .withPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()))
                    .withPrivacyLevel(propertyForm.getPrivacyLevel())
                    .withCapacity(propertyForm.getCapacity())
                    .withMainImageId(propertyForm.getMainImageId())
                    .withServices(generateObjects(propertyForm.getServiceIds(), Service::new))
                    .withRules(generateObjects(propertyForm.getRuleIds(), Rule::new))
                    .withImages(generateObjects(propertyForm.getImageIds(), Image::new))
                    .build()
        );
        if(propertyOrErrors.hasValue()){
            return new ModelAndView("redirect:/" + propertyOrErrors.value().getId());
        }
        else{
            System.out.println(propertyOrErrors.alternative().toString());
            return create(propertyOrErrors.alternative());
        }
    }

    private <T> Collection<T> generateObjects(long[] objectIds, LongFunction<T> function) {
        if(objectIds != null && objectIds.length > 0)
            return Arrays.stream(objectIds).mapToObj(function).collect(Collectors.toList());
        else
            return new LinkedList<>();
    }
}
