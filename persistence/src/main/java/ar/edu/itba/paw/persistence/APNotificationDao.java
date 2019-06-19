package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.Paginator;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.enums.NotificationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class APNotificationDao implements NotificationDao {

    private static final String USER_NOTIFICATIONS_QUERY = "FROM Notification n WHERE n.user.id = :id ORDER BY n.id DESC";
    private static final String UNREAD_USER_NOTIFICATIONS_QUERY = "FROM Notification n " +
                                                                    "WHERE n.state = 'UNREAD' AND n.user.id = :id " +
                                                                    "ORDER BY n.id DESC";

    @Autowired
    private Paginator paginator;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Notification get(long id) {
        return entityManager.find(Notification.class, id);
    }

    @Override
    public Collection<Notification> getAll(PageRequest pageRequest) {
        TypedQuery<Notification> query = entityManager.createQuery("FROM Notification", Notification.class);
        return paginator.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    public Collection<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest) {
        TypedQuery<Notification> query = entityManager
                                    .createQuery(USER_NOTIFICATIONS_QUERY, Notification.class);
        query.setParameter("id", id);
        return paginator.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    public Collection<Notification> getAllUnreadNotificationsForUser(long id, PageRequest pageRequest) {
        TypedQuery<Notification> query = entityManager
                .createQuery(UNREAD_USER_NOTIFICATIONS_QUERY, Notification.class);
        query.setParameter("id", id);
        return paginator.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    @Transactional
    public Notification createNotification(Notification notification) {
        if (notification != null)
            entityManager.persist(notification);
        return notification;
    }

    @Override
    @Transactional
    public void markRead(long notificationId) {
        Notification notification = get(notificationId);
        notification.setState(NotificationState.READ);
        entityManager.merge(notification);
    }
}
