package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Notification;

import java.util.Collection;
import java.util.List;

public interface NotificationDao extends Dao<Notification>{
    List<Notification> getAllNotificationsForUser(long id);
    List<Notification> getAllUnreadNotificationsForUser(long id);
    Notification createNotification(long userId, String subjectCode, String textCode, String link);
}
