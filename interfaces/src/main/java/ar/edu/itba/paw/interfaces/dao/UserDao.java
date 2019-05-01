package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.User;

import java.util.stream.Stream;

public interface UserDao extends Dao<User> {
    Stream<User> getAllAsStream();
    User getByUsername(String username);
    User create(User user);
}
