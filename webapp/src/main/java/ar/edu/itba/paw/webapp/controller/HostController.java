package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.interfaces.service.ProposalService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.Availability;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.model.exceptions.IllegalPropertyStateException;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import ar.edu.itba.paw.webapp.form.PropertyCreationForm;
import ar.edu.itba.paw.webapp.form.ProposalForm;
import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

@Controller
@RequestMapping("host/")
public class HostController {

    @Autowired
    private NavigationUtility navigationUtility;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/decline/{proposalId}", method = RequestMethod.POST )
    public ModelAndView hostDecline(@PathVariable(value = "proposalId") int proposalId,
                                    @Valid @ModelAttribute("proposalForm") ProposalForm form) {
        if (!userOwnsProposalProperty(proposalId))
            return navigationUtility.mavWithGeneralNavigationAttributes("404");

        proposalService.setDecline(proposalId);

        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    @RequestMapping(value = "/accept/{proposalId}", method = RequestMethod.POST )
    public ModelAndView hostAccept(@PathVariable(value = "proposalId") int proposalId,
                                   @Valid @ModelAttribute("proposalForm") ProposalForm form) {
        if (!userOwnsProposalProperty(proposalId))
            return navigationUtility.mavWithGeneralNavigationAttributes("404");

        proposalService.setAccept(proposalId);

        return new ModelAndView("redirect:/proposal/" + proposalId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("propertyCreationForm") final PropertyCreationForm form,
                               @ModelAttribute("filteredSearchForm") FilteredSearchForm searchForm) {
        return navigationUtility.mavWithGeneralNavigationAttributes("createProperty");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
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

    private boolean userOwnsProposalProperty(long proposalId){
        User u = navigationUtility.getCurrentlyLoggedUser();
        Proposal proposal = proposalService.getWithRelatedEntities(proposalId);
        return (proposal != null && proposal.getProperty().getOwner().getId() == u.getId());
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

    private ModelAndView create(Collection<String> errors) {
        ModelAndView mav = navigationUtility.mavWithGeneralNavigationAttributes("createProperty");
        mav.addObject("errors", errors);
        return mav;
    }
}
