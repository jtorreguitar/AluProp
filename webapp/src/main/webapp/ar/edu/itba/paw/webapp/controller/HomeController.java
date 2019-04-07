package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.interfaces.IPropertyService;

@Controller
public class HomeController {

    @Autowired
    private IPropertyService propertyService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(required = true, name = "propertyId") int id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", propertyService.get(id).getCaption());
        return mav;
    }
}