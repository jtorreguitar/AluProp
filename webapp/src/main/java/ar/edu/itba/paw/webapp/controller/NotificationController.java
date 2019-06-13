package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.utilities.UserUtility;
import ar.edu.itba.paw.webapp.form.FilteredSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private NeighbourhoodService neighbourhoodService;

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public ModelAndView notifications(@ModelAttribute FilteredSearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("notifications");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = UserUtility.getCurrentlyLoggedUser(SecurityContextHolder.getContext(), userService);
        mav.addObject("userRole", auth.getAuthorities());
        mav.addObject("currentUser", user);
        addSearchObjectsToMav(mav);
        if (user != null)
            addNotificationsToMav(mav, user);
        return mav;
    }

    private void addSearchObjectsToMav(ModelAndView mav){
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
    }

    private void addNotificationsToMav(ModelAndView mav, User u){
        List<Notification> notifications = notificationService.getAllNotificationsForUser(u.getId(), new PageRequest(0, 5));
        mav.addObject("notifications", notifications);
    }

}
