package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.User;

public interface UserDao extends Dao<User> {
    User getByUsername(String username);
    User create(User user);
}
