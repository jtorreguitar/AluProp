package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Service
public class APNotificationService implements NotificationService {
    @Autowired
    NotificationDao notificationDao;


    @Override
    public List<Notification> getAllNotificationsForUser(long id) {
        return notificationDao.getAllNotificationsForUser(id);
    }

    @Override
    public List<Notification> getAllUnreadNotificationsForUser(long id) {
        return notificationDao.getAllUnreadNotificationsForUser(id);
    }
}
