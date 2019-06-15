package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.PropertyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Component
public class NotificationUtility {
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    NotificationService notificationService;

    public void sendNotifications(String subjectCode, String textCode, String link, Collection<User> users, long currentUserId){
        for (User user: users){
            if (user.getId() == currentUserId)
                continue;
            Notification result = notificationService.createNotification(user.getId(), subjectCode, textCode, link);
            if (result == null)
                logger.error("Failed to deliver notification to user with id: " + user.getId());
        }
    }

    public void addNotificationsToMav(ModelAndView mav, User u){
        Collection<Notification> notifications = notificationService.getAllNotificationsForUser(u.getId(), new PageRequest(0, 5));
        mav.addObject("notifications", notifications);
    }
}
