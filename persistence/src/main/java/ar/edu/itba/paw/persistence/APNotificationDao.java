package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Repository
public class APNotificationDao implements NotificationDao {

    @Override
    public Notification get(long id) {
        return null;
    }

    @Override
    public Collection<Notification> getAll(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Notification> getAllNotificationsForUser(long id) {
        ArrayList<Notification> dummyNotifications = new ArrayList<>();
        dummyNotifications.add(new Notification(0, 2, "notifications.proposals.sent.subject", "notifications.proposals.sent", "/proposal/14", 0));
        dummyNotifications.add(new Notification(0, 3, "notifications.proposals.sent.subject", "notifications.proposals.sent", "/proposal/14", 0));
        dummyNotifications.add(new Notification(1, 3, "notifications.proposals.hostProposal.subject", "notifications.proposals.hostProposal", "/proposal/14", 0));
        return dummyNotifications;
    }

    @Override
    public List<Notification> getAllUnreadNotificationsForUser(long id) {
        return null;
    }
}
