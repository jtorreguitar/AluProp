package ar.edu.itba.paw.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.interfaces.service.PropertyService;

@org.springframework.stereotype.Service
public class APPropertyService implements PropertyService {

    private final String PROPERTY_NOT_FOUND = "desired property not found";
    private final String USER_NOT_FOUND = "user not found";
    private final String DATABASE_ERROR = "Database error";

    private List<String> errors;

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ServiceDao serviceDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private RuleDao ruleDao;
    @Autowired
    private NeighbourhoodDao neighbourhoodDao;

    @Override
    public Property get(long id) {
        return propertyDao.get(id);
    }

    @Override
    public Collection<Property> getAll() {
        return propertyDao.getAll();
    }

    @Override
    public List<String> showInterestOrReturnErrors(long propertyId, String username) {
        errors = new LinkedList<>();
        User user = userDao.getByEmail(username);
        CheckUserAndPropertyExist(propertyId, user);
        if (!errors.isEmpty()) 
            return errors;
        boolean wasCreated = propertyDao.showInterest(propertyId, user);
        if(!wasCreated) 
            errors.add(DATABASE_ERROR);
        return errors;
    }

    private void CheckUserAndPropertyExist(long propertyId, User user) {
        if (propertyDao.get(propertyId) == null)
            errors.add(PROPERTY_NOT_FOUND);
        if(user == null)
            errors.add(USER_NOT_FOUND);
    }

    @Override
    public Property getPropertyWithRelatedEntities(long id) {
        return propertyDao.getPropertyWithRelatedEntities(id);
    }

    @Override
    public Either<Property, Collection<String>> create(Property property) {
        errors = new LinkedList<>();
        checkRelatedEntitiesExist(property);
        if(!errors.isEmpty())
            return Either.alternativeFrom(errors);
        return Either.valueFrom(propertyDao.create(property));
    }

    private void checkRelatedEntitiesExist(Property property) {
        List<Image> images = new LinkedList<>(property.getImages());
        images.add(new Image(property.getMainImageId()));
        checkImagesExist(images);
        checkServicesExist(property.getServices());
        checkRulesExist(property.getRules());
        checkNeighbourhoodExists(property.getNeighbourhoodId());
    }

    private void checkImagesExist(Collection<Image> images) {
        for (Image image: images) {
            if(imageDao.get(image.getId()) == null) {
                errors.add("One of the images specified does not exist");
                return;
            }
        }
    }

    private void checkServicesExist(Collection<Service> services) {
        for (Service service: services) {
            if(serviceDao.get(service.getId()) == null) {
                errors.add("One of the services specified does not exist");
                return;
            }
        }
    }

    private void checkRulesExist(Collection<Rule> rules) {
        for (Rule rule:rules) {
            if(ruleDao.get(rule.getId()) == null) {
                errors.add("One of the rules specified does not exist");
                return;
            }
        }
    }

    private void checkNeighbourhoodExists(long id) {
        if(neighbourhoodDao.get(id) == null) {
            errors.add("The neighbourhood specified does not exist");
        }
    }
}
