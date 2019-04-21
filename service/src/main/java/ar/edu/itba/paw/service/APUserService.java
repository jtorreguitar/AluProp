package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class APUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User get(long id) {
        return userDao.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }
}
