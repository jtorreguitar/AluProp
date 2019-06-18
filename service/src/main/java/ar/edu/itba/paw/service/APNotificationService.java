package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.NotificationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

@Service
public class APNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest) {
        return notificationDao.getAllNotificationsForUser(id, pageRequest);
    }

    @Override
    public Collection<Notification> getAllUnreadNotificationsForUser(long id, PageRequest pageRequest) {
        return notificationDao.getAllUnreadNotificationsForUser(id, pageRequest);
    }

    @Override
    public Notification createNotification(long userId, String subjectCode, String textCode, String link) {
        Notification.Builder builder = new Notification.Builder();
        builder.withUser(userDao.get(userId));
        builder.withSubjectCode(subjectCode);
        builder.withTextCode(textCode);
        builder.withLink(link);
        builder.withState(NotificationState.UNREAD);
        return notificationDao.createNotification(builder.build());
    }

    @Override
    public void sendNotifications(String subjectCode, String textCode, String link, Collection<User> users, long currentUserId){
        for (User user: users){
            if (user.getId() == currentUserId)
                continue;
            Notification result = createNotification(user.getId(), subjectCode, textCode, link);
            if (result == null)
                logger.error("Failed to deliver notification to user with id: " + user.getId());
        }
    }

    @Override
    public void sendNotification(String subjectCode, String textCode, String link, User user){
        Notification result = createNotification(user.getId(), subjectCode, textCode, link);
        if (result == null)
            logger.error("Failed to deliver notification to user with id: " + user.getId());
    }

    @Override
    public void markRead(long notificationId) {
        notificationDao.markRead(notificationId);
    }
}
