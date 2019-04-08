package ar.edu.itba.paw.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.IPropertyDao;
import ar.edu.itba.paw.interfaces.IPropertyService;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;

@Service
public class PropertyService implements IPropertyService {

    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final String INVALID_EMAIL_STRING = "the given email is invalid";
    private final String PROPERTY_NOT_FOUND_STRING = "desired property not found";

    @Autowired
    private IPropertyDao propertyDao;

    @Override
    public Property get(int id) {
        return propertyDao.get(id);
    }

    @Override
    public Collection<Property> getAll() {
        return propertyDao.getAll();
    }

    @Override
    public Either<Interest, List<String>> interest(int propertyId, String email, String description) {
        List<String> errors = new LinkedList<String>();
        if(email == null || email.isEmpty() || !Pattern.compile(EMAIL_REGEX).matcher(email).matches())
            errors.add(INVALID_EMAIL_STRING);
        else if(propertyDao.get(propertyId) == null)
            errors.add(PROPERTY_NOT_FOUND_STRING);
        if(!errors.isEmpty())
            return Either.alternativeFrom(errors);
        return Either.valueFrom(propertyDao.interest(propertyId, email, description));
    }

}