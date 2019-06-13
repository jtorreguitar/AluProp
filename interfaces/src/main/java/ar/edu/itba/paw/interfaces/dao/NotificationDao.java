package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.model.Notification;

import java.util.Collection;
import java.util.List;

public interface NotificationDao extends Dao<Notification>{
    List<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest);
    List<Notification> getAllUnreadNotificationsForUser(long id);
    Notification createNotification(Notification notification);
}
