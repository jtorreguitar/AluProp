package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class APNotificationService implements NotificationService {

    @Autowired
    NotificationDao notificationDao;

    @Override
    public Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest) {
        return notificationDao.getAllNotificationsForUser(id, pageRequest);
    }

    @Override
    public Collection<Notification> getAllUnreadNotificationsForUser(long id) {
        return notificationDao.getAllUnreadNotificationsForUser(id);
    }

    @Override
    public Notification createNotification(long userId, String subjectCode, String textCode, String link) {
        Notification.Builder builder = new Notification.Builder();
        builder.withUserId(userId);
        builder.withSubjectCode(subjectCode);
        builder.withTextCode(textCode);
        builder.withLink(link);
        return notificationDao.createNotification(builder.build());
    }
}
