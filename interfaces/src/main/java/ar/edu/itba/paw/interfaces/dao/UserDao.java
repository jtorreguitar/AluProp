package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.User;

import java.util.Collection;
import java.util.stream.Stream;

public interface UserDao {
    Collection<User> getAll();
    User get(long id);
    User getByEmail(String email);
    User create(User user);
}
