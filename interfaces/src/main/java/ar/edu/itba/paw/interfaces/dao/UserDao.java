package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.User;

import java.util.stream.Stream;

public interface UserDao extends Dao<User> {
    Stream<User> getAllAsStream();
    User getByEmail(String username);
    User create(User user);
    User getWithRelatedEntities(long id);
}
