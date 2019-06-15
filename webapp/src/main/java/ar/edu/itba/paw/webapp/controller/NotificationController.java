package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.utilities.NavigationUtility;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotificationController {

    @Autowired
    private NavigationUtility navigationUtility;

    @RequestMapping(value = "/user/notifications", method = RequestMethod.GET)
    public ModelAndView notifications(@ModelAttribute FilteredSearchForm searchForm) {
        return navigationUtility.mavWithGeneralNavigationAttributes("notifications");
    }

}
