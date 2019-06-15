package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.interfaces.IdNamePair;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Component
public class NavigationUtility {

    private static final int NOTIFICATIONS_FOR_FIRST_PAGE = 5;
    private static final IdNamePair[] PROPERTY_TYPES = {new IdNamePair(0, "forms.house"),
                                                        new IdNamePair(1, "forms.apartment"),
                                                        new IdNamePair(2, "forms.loft")};
    private static final IdNamePair[] PRIVACY_LEVELS = {new IdNamePair(0, "forms.privacy.individual"),
                                                        new IdNamePair(1, "forms.privacy.shared")};
    private static final PageRequest NOTIFICATION_PAGE_REQUEST = new PageRequest(0, NOTIFICATIONS_FOR_FIRST_PAGE);

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NeighbourhoodService neighbourhoodService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private ServiceService serviceService;

    public String getUsernameOfCurrentlyLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else return principal.toString();
    }

    public User getCurrentlyLoggedUser() {
        return userService.getByEmail(getUsernameOfCurrentlyLoggedUser());
    }

    public void addPaginationAttributes(ModelAndView mav, PageResponse<Property> response, int maxItems) {
        mav.addObject("properties", response.getResponseData());
        mav.addObject("currentPage", response.getPageNumber());
        mav.addObject("totalPages", response.getTotalPages());
        mav.addObject("totalElements", response.getTotalItems());
        mav.addObject("maxItems",maxItems);
    }

    public void addGeneralNavigationAttributes(ModelAndView mav) {
        User user = getCurrentlyLoggedUser();
        if(user != null) {
            Collection<Notification> notifications =
                    notificationService.getAllNotificationsForUser(user.getId(), NOTIFICATION_PAGE_REQUEST);
            mav.addObject("currentUser", user);
            mav.addObject("userRole", user.getRole().toString());
            mav.addObject("notifications", notifications);
        }
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
        mav.addObject("propertyTypes", PROPERTY_TYPES);
        mav.addObject("privacyLevels", PRIVACY_LEVELS);
    }

    public ModelAndView mavWithGeneralNavigationAttributes(String view) {
        final ModelAndView mav = new ModelAndView(view);
        addGeneralNavigationAttributes(mav);
        return mav;
    }

    public ModelAndView mavWithGeneralNavigationAttributes() {
        final ModelAndView mav = new ModelAndView();
        addGeneralNavigationAttributes(mav);
        return mav;
    }
}
