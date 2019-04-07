package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.IPropertyService;

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
}