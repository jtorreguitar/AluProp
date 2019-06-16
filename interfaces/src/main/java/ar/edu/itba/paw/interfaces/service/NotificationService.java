package ar.edu.itba.paw.interfaces.service;

import java.util.Collection;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;

public interface NotificationService {
    Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest);
    Collection<Notification> getAllUnreadNotificationsForUser(long id, PageRequest pageRequest);
    Notification createNotification(long userId, String subjectCode, String textCode, String link);
    void sendNotifications(String subjectCode, String textCode, String link, Collection<User> users, long currentUserId);
    void sendNotification(String subjectCode, String textCode, String link, User user);
}
