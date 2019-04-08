package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.IPropertyService;
import ar.edu.itba.paw.model.Interest;

@Controller
@RequestMapping("/")
public class PropertyController {

    @Autowired
    private IPropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", propertyService.getAll());
        return mav;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("id") int id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", propertyService.get(id).getCaption());
        return mav;
    }

    @RequestMapping(value = "{id}/interest", method = RequestMethod.POST)
    public ModelAndView interest(@PathVariable("id") int propertyId,
                                @RequestParam(value = "email", required = true) String email,
                                @RequestParam(value = "description") String description) {
        final Either<Interest, List<String>> interest = propertyService.interest(propertyId, email, description);
        if(interest.hasValue())
            return new ModelAndView("redirect:/" + interest.value().getPropertyId());
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("errors", interest.alternative());
        return mav;
    }
}