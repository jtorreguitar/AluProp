package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.User;

public interface UserService {
    User get(long id);
    User getByEmail(String username);
    User CreateUser(User user);
}
