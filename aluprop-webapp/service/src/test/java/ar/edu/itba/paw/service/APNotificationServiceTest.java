package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class APNotificationServiceTest {
    /* package */ static String SUBJECT_CODE="this is a test";
    /* package */ static String TEXT_CODE="this is a test";
    /* package */ static String LINK="/this/is/test";


    @InjectMocks
    private APNotificationService notificationService;

    @Mock
    private UserDao userDao;

    @Mock
    private NotificationDao notificationDao;


    @Test
    public void createNotificationTest(){
        Notification expectedNotification = Factories.notificationCreator();
        User notifiedUser = Factories.userCreator();
        Mockito.when(notificationDao.createNotification(any(Notification.class)))
                .thenReturn(expectedNotification);
        Mockito.when(userDao.get(notifiedUser.getId())).thenReturn(notifiedUser);

        Notification realNotification = notificationService.createNotification(
                notifiedUser.getId(),
                SUBJECT_CODE,
                TEXT_CODE,
                LINK);

        Assert.assertNotNull(realNotification);
        Assert.assertEquals(expectedNotification.getTextCode(), realNotification.getTextCode());
        Assert.assertEquals(expectedNotification.getSubjectCode(), realNotification.getSubjectCode());
        Assert.assertEquals(expectedNotification.getLink(), realNotification.getLink());

    }




}
