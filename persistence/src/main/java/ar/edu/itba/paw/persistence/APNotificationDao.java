package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.persistence.utilities.QueryUtility;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Repository
public class APNotificationDao implements NotificationDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Notification get(long id) {
        return entityManager.find(Notification.class, id);
    }

    @Override
    public Collection<Notification> getAll(PageRequest pageRequest) {
        TypedQuery<Notification> query = entityManager.createQuery("FROM Notification", Notification.class);
        return QueryUtility.makePagedQuery(query, pageRequest).getResultList();
    }

    @Override
    public List<Notification> getAllNotificationsForUser(long id, PageRequest pageRequest) {
        return entityManager.createQuery("FROM Notification", Notification.class).getResultList();
    }

    @Override
    public List<Notification> getAllUnreadNotificationsForUser(long id) {
        return null;
    }

    @Override
    @Transactional
    public Notification createNotification(Notification notification){//long userId, String subjectCode, String textCode, String link) {
        if (notification != null)
            entityManager.persist(notification);
        return notification;
    }
}
