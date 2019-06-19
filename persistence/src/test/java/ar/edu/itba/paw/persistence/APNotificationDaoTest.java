package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.enums.NotificationState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APNotificationDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NotificationDao notificationDao;

    PageRequest pageRequest;

    @Before
    public void setUp(){
        pageRequest = new PageRequest(0, 9);
    }

    @Test
    public void getAllNotificationsReturnsAllNotificationsTest(){
        Long realCount = entityManager.createQuery("SELECT Count(n.id) FROM Notification n", Long.class)
                            .getSingleResult();

        Long expectedCount = Long.valueOf(notificationDao.getAll(pageRequest).size());
        Assert.assertEquals(realCount,expectedCount);
    }

    @Test
    public void getAllUnreadNotificationsForUserReturnsOnlyUnreadNotificationsTest(){
        Collection<Notification> expectedUnreadNotifications = notificationDao.getAllUnreadNotificationsForUser(1, pageRequest);

        for(Notification notif : expectedUnreadNotifications){
            Assert.assertEquals(NotificationState.UNREAD, notif.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void markReadChangesNotificationStateInDatabaseTest(){
        Long previousUnreadNotifications = entityManager.createQuery("SELECT Count(n.id) FROM Notification n WHERE n.state='UNREAD'", Long.class)
                                           .getSingleResult();

        notificationDao.markRead(1);

        Long newUnreadNotifications = entityManager.createQuery("SELECT Count(n.id) FROM Notification n WHERE n.state='UNREAD'", Long.class)
                                      .getSingleResult();

        Assert.assertEquals( previousUnreadNotifications-1, (long) newUnreadNotifications);
    }
}
