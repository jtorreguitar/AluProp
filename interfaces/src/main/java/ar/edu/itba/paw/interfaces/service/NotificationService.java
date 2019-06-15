package ar.edu.itba.paw.interfaces.service;

import java.util.Collection;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.model.Notification;

public interface NotificationService {
    Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest);
    Collection<Notification> getAllUnreadNotificationsForUser(long id);
    Notification createNotification(long userId, String subjectCode, String textCode, String link);
}
