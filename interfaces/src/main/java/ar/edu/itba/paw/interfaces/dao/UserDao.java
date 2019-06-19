package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.model.User;

import java.util.Collection;

public interface UserDao {
    Collection<User> getAll();
    User get(long id);

    User getWithRelatedEntities(long id);

    User getByEmail(String email);
    User getUserWithRelatedEntitiesByEmail(String email);
    User create(User user);
    boolean userExistsByEmail(String email);
    Collection<User> getUsersInterestedInProperty(long id, PageRequest pageRequest);
    Long count();
}
