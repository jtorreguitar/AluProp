package ar.edu.itba.paw.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.model.Property;

@Service
public class APPropertyService implements PropertyService {

    private final String PROPERTY_NOT_FOUND = "desired property not found";
    private final String USER_NOT_FOUND = "user not found";
    private final String DATABASE_ERROR = "Database error";

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Property get(int id) {
        return propertyDao.get(id);
    }

    @Override
    public Collection<Property> getAll() {
        return propertyDao.getAll();
    }

    @Override
    public List<String> showInterestOrReturnErrors(int propertyId, String username) {
        List<String> errors = new LinkedList<>();
        User user = userDao.getByUsername(username);
        CheckUserAndPropertyExist(propertyId, errors, user);
        if (!errors.isEmpty()) 
            return errors;
        boolean wasCreated = propertyDao.showInterest(propertyId, user);
        if(!wasCreated) 
            errors.add(DATABASE_ERROR);
        return errors;
    }

    private void CheckUserAndPropertyExist(int propertyId, List<String> errors, User user) {
        if (propertyDao.get(propertyId) == null)
            errors.add(PROPERTY_NOT_FOUND);
        if(user == null)
            errors.add(USER_NOT_FOUND);
    }

}
