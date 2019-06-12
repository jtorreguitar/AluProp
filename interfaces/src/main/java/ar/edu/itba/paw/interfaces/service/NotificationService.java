package ar.edu.itba.paw.interfaces.service;

import java.util.Collection;
import java.util.List;


import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import org.springframework.lang.Nullable;

public interface NotificationService {
    List<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest);
    List<Notification> getAllUnreadNotificationsForUser(long id);
    Notification createNotification(long userId, String subjectCode, String textCode, String link);
}
