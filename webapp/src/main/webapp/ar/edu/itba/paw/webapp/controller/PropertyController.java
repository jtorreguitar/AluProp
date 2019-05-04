package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.webapp.Utilities.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.service.PropertyService;

@Controller
@RequestMapping("/")
public class PropertyController {

    @Autowired private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("properties", propertyService.getAll());
        return mav;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") long id) {
        final ModelAndView mav = new ModelAndView("detailedProperty");
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
}
