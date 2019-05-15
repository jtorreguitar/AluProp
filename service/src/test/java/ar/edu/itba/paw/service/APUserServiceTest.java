package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Career;
import ar.edu.itba.paw.model.University;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class APUserServiceTest {

    /* package */ static final int UNIVERSITY_ID = 1;
    /* package */ static final String UNIVERSITY_NAME = "TEST_UNI";

    /* package */ static final int CAREER_ID = 1;
    /* package */ static final String CAREER_NAME = "TEST_CAREER";

    /* package */ static final String EMAIL = "dummyUser@gmail.com";
    /* package */ static final String PASSWORD = "verySafePassword";
    /* package */ static final String NAME = "John";
    /* package */ static final String LAST_NAME = "Doe";
    /* package */ static final String BIO = "Hi I'm john and I love testing";

    @InjectMocks
    private APUserService userService = new APUserService();

    @Mock
    private UserDao mockUserDao;

    @Mock
    private UniversityDao mockUniversityDao;

    @Mock
    private CareerDao mockCareerDao;

    @Mock
    PageResponse<User> pageResponse;

    @Mock
    PageRequest pageRequest;

    @InjectMocks
    private User user;

    private User createDummyUser(){
        User.Builder builder = new User.Builder();
        return builder.withEmail(EMAIL)
                .withName(NAME)
                .withLastName(LAST_NAME)
                .withBirthDate(Date.valueOf("1996-07-12"))
                .withGender(Gender.MALE)
                .withPasswordHash(PASSWORD)
                .withUniversityId(UNIVERSITY_ID)
                .withCareerId(CAREER_ID)
                .withBio(BIO)
                .withContactNumber("1166765456")
                .withRole(Role.ROLE_GUEST)
                .build();
    }
    @Before
    public void setUp(){

        user = createDummyUser();

        Mockito.when(mockUniversityDao.get(UNIVERSITY_ID))
                .thenReturn(new University(UNIVERSITY_ID, UNIVERSITY_NAME, null));
        Mockito.when(mockCareerDao.get(CAREER_ID))
                .thenReturn(new Career(CAREER_ID, CAREER_NAME));

    }

    @Test
    public void createValidUserTest(){

        Mockito.when(mockUserDao.create(user))
                .thenReturn(user);

        Either<User, List<String>> maybeUser = userService.CreateUser(user);

        Assert.assertNotNull(maybeUser);
        Assert.assertTrue(maybeUser.hasValue());
        Assert.assertEquals(NAME, maybeUser.value().getName());
        Assert.assertEquals(PASSWORD, maybeUser.value().getPasswordHash());

    }

    @Test
    public void createInvalidUserTest(){

        Mockito.when(mockUniversityDao.get(UNIVERSITY_ID))
                .thenReturn(null);

        Mockito.when(mockCareerDao.get(CAREER_ID))
                .thenReturn(null);

        Either<User, List<String>> maybeUser = userService.CreateUser(user);

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.hasValue());
        Assert.assertNotNull(maybeUser.alternative());
        Assert.assertEquals(2, maybeUser.alternative().size());
        Assert.assertEquals(APUserService.UNIVERSITY_NOT_EXISTS, maybeUser.alternative().get(0));
        Assert.assertEquals(APUserService.CAREER_NOT_EXISTS, maybeUser.alternative().get(1));

    }

    @Test
    public void createUserWithInvalidUniversityOnlyTest(){

        Mockito.when(mockUniversityDao.get(UNIVERSITY_ID))
                .thenReturn(null);


        Either<User, List<String>> maybeUser = userService.CreateUser(user);

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.hasValue());
        Assert.assertNotNull(maybeUser.alternative());
        Assert.assertEquals(1, maybeUser.alternative().size());
        Assert.assertEquals(APUserService.UNIVERSITY_NOT_EXISTS, maybeUser.alternative().get(0));
    }

    @Test
    public void createUserWithInvalidCareerOnlyTest(){

        Mockito.when(mockCareerDao.get(CAREER_ID))
                .thenReturn(null);

        Either<User, List<String>> maybeUser = userService.CreateUser(user);

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.hasValue());
        Assert.assertNotNull(maybeUser.alternative());
        Assert.assertEquals(1, maybeUser.alternative().size());
        Assert.assertEquals(APUserService.CAREER_NOT_EXISTS, maybeUser.alternative().get(0));

    }

    @Test
    public void getUserWithRelatedEntitiesByEmailTest(){
        Mockito.when(mockUserDao.getUserWithRelatedEntitiesByEmail(EMAIL))
                .thenReturn(user);

        User relatedEntitiesUser = userService.getUserWithRelatedEntitiesByEmail(EMAIL);

        Assert.assertNotNull(relatedEntitiesUser);
        Assert.assertEquals(EMAIL ,relatedEntitiesUser.getEmail());
    }

    @Test
    public void getUsersInterestedInProperty(){
        Mockito.when(mockUserDao.getUsersInterestedInProperty(1, pageRequest))
                .thenReturn(pageResponse);

        PageResponse<User> pr = userService.getUsersInterestedInProperty(1, pageRequest);

        Assert.assertNotNull(pr);
        Assert.assertEquals(pageResponse, pr);
    }

}
