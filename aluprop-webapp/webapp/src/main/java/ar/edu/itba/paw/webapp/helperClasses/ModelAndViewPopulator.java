package ar.edu.itba.paw.webapp.helperClasses;

import ar.edu.itba.paw.interfaces.IdNamePair;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Component
public class ModelAndViewPopulator {

    private static final int NOTIFICATIONS_FOR_FIRST_PAGE = 5;
    private static final IdNamePair[] PROPERTY_TYPES = { new IdNamePair(0, "forms.house"),
                                                         new IdNamePair(1, "forms.apartment"),
                                                         new IdNamePair(2, "forms.loft") };
    private static final IdNamePair[] PRIVACY_LEVELS = { new IdNamePair(0, "forms.privacy.individual"),
                                                         new IdNamePair(1, "forms.privacy.shared") };
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
    @Autowired
    private ProposalService proposalService;

    public void addPaginationAttributes(ModelAndView mav, PageResponse<Property> response) {
        mav.addObject("properties", response.getResponseData());
        mav.addObject("currentPage", response.getPageNumber());
        mav.addObject("totalPages", response.getTotalPages());
        mav.addObject("totalElements", response.getTotalItems());
    }

    public void addNavigationAttributes(ModelAndView mav) {
        User user = userService.getCurrentlyLoggedUser();
        if(user != null) {
            Collection<Notification> unreadNotifications =
                    notificationService.getAllUnreadNotificationsForUser(user.getId(), NOTIFICATION_PAGE_REQUEST);
            Collection<Notification> notifications =
                    notificationService.getAllNotificationsForUser(user.getId(), NOTIFICATION_PAGE_REQUEST);
            Proposal[] unreadNotificationProposals = getProposalsForNotifications((List<Notification>)unreadNotifications);
            Proposal[] notificationProposals = getProposalsForNotifications((List<Notification>)notifications);
            mav.addObject("currentUser", user);
            mav.addObject("unreadNotifications", unreadNotifications);
            mav.addObject("unreadNotificationProposals", unreadNotificationProposals);
            mav.addObject("notifications", notifications);
            mav.addObject("notificationProposals", notificationProposals);

        }
        mav.addObject("neighbourhoods", neighbourhoodService.getAll());
        mav.addObject("rules", ruleService.getAll());
        mav.addObject("services", serviceService.getAll());
        mav.addObject("propertyTypes", PROPERTY_TYPES);
        mav.addObject("privacyLevels", PRIVACY_LEVELS);
    }

    public ModelAndView mavWithNavigationAttributes(String view) {
        final ModelAndView mav = new ModelAndView(view);
        addNavigationAttributes(mav);
        return mav;
    }

    public ModelAndView mavWithNavigationAttributes() {
        final ModelAndView mav = new ModelAndView();
        addNavigationAttributes(mav);
        return mav;
    }

    private Proposal[] getProposalsForNotifications(List<Notification> notifications){
        Proposal[] result = new Proposal[notifications.size()];
        for (int i = 0; i < notifications.size(); i++)
            result[i] = proposalService.get(Integer.parseInt(notifications.get(i).getLink().split("proposal/")[1]));
        return result;
    }
}
