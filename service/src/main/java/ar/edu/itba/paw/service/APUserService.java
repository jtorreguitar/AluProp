package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class APUserService implements UserService {

    private final String USER_EXISTS_BY_EMAIL = "A user with this email already exists";
    /* package */ static final String UNIVERSITY_NOT_EXISTS = "The specified university does not exist";
    /* package */ static final String CAREER_NOT_EXISTS = "The specified career does not exist";

    @Autowired
    private UserDao userDao;
    @Autowired
    private UniversityDao universityDao;
    @Autowired
    private CareerDao careerDao;

    private List<String> errors;

    @Override
    public User get(long id) {
        return userDao.get(id);
    }

    @Override
    public User getWithRelatedEntities(long id) {
        return userDao.getWithRelatedEntities(id);
    }

    @Override
    public User getByEmail(String username) {
        return userDao.getByEmail(username);
    }

    @Override
    public Either<User, List<String>> CreateUser(User user) {
        errors = new LinkedList<>();
        if (user.getRole() == Role.ROLE_GUEST)
            checkRelatedEntitiesExist(user);
        if(userDao.userExistsByEmail(user.getEmail()))
            errors.add(USER_EXISTS_BY_EMAIL);

        if(!errors.isEmpty())
            return Either.alternativeFrom(errors);
        return Either.valueFrom(userDao.create(user));
    }
    
    private void checkRelatedEntitiesExist(User user) {
        checkUniversityExists(user.getUniversity().getId());
        checkCareerExists(user.getCareer().getId());
    }

    private void checkUniversityExists(long universityId) {
        if(universityDao.get(universityId) == null)
            errors.add(UNIVERSITY_NOT_EXISTS);
    }

    private void checkCareerExists(long careerId) {
        if(careerDao.get(careerId) == null)
            errors.add(CAREER_NOT_EXISTS);
    }

    @Override
    public User getUserWithRelatedEntitiesByEmail(String email) {
        return userDao.getUserWithRelatedEntitiesByEmail(email);
    }

    @Override
    public PageResponse<User> getUsersInterestedInProperty(long id, PageRequest pageRequest) {
        Collection<User> data = userDao.getUsersInterestedInProperty(id, pageRequest);
        long count = userDao.count();
        return new PageResponse<>(pageRequest, count, data);
    }

    @Override
    public boolean getUserIsInterestedInProperty(long userId, long propertyId) {
        return userDao.isUserInterestedInProperty(userId, propertyId);
    }

    @Override
    public User getCurrentlyLoggedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth == null)
            return null;
        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails)
            return userDao.getUserWithRelatedEntitiesByEmail(((UserDetails) principal).getUsername());
        else return userDao.getUserWithRelatedEntitiesByEmail(principal.toString());
    }
}
