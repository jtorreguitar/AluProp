package ar.edu.itba.paw.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.PropertyDao;
import ar.edu.itba.paw.interfaces.PropertyService;
import ar.edu.itba.paw.model.Property;

@Service
public class APPropertyService implements PropertyService {

    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final String INVALID_EMAIL_STRING = "the given email is invalid";
    private final String PROPERTY_NOT_FOUND_STRING = "desired property not found";

    @Autowired
    private PropertyDao propertyDao;

    @Override
    public Property get(int id) {
        return propertyDao.get(id);
    }

    @Override
    public Collection<Property> getAll() {
        return propertyDao.getAll();
    }

    @Override
    public List<String> showInterestOrReturnErrors(int propertyId, String email, String description) {
        List<String> errors = validateInterestValues(propertyId, email);
        if (!errors.isEmpty()) 
            return errors;
        boolean wasCreated = propertyDao.showInterest(propertyId, email, description);
        if(!wasCreated) 
            errors.add("Database error");
        return errors;
    }

    private List<String> validateInterestValues(int propertyId, String email) {
        List<String> errors = new LinkedList<String>();
        if (email == null || email.isEmpty() || !Pattern.compile(EMAIL_REGEX).matcher(email).matches())
            errors.add(INVALID_EMAIL_STRING);
        else if (propertyDao.get(propertyId) == null)
            errors.add(PROPERTY_NOT_FOUND_STRING);
        return errors;
    }

}
