package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.model.Notification;

import java.util.Collection;

public interface NotificationDao {
    Notification get(long id);
    Collection<Notification> getAll(PageRequest pageRequest);
    Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest);
    Collection<Notification> getAllUnreadNotificationsForUser(long id, PageRequest pageRequest);
    Notification createNotification(Notification notification);
    void markRead(long notificationId);
}
