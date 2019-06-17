package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.interfaces.service.PropertyService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.Availability;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.HttpURLConnection;
import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Service
public class APPropertyService implements PropertyService {

    /* package */ final static String PROPERTY_NOT_FOUND = "desired property not found";
    /* package */ final static String USER_NOT_FOUND = "user not found";
    /* package */ final static String DATABASE_ERROR = "Database error";
    /* package */ final static String IMAGE_NOT_EXISTS = "One of the images specified does not exist";
    /* package */ final static String SERVICE_NOT_EXISTS = "One of the services specified does not exist";
    /* package */ final static String RULE_NOT_EXISTS ="One of the rules specified does not exist";
    /* package */ final static String NEIGHBOURHOOD_NOT_EXISTS = "The neighbourhood specified does not exist";
    private final int DEFAULT_PAGE_SIZE = 12;
    private final int DEFAULT_PAGE_NUMBER = 0;
    private List<String> errors;

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private ServiceDao serviceDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private RuleDao ruleDao;
    @Autowired
    private NeighbourhoodDao neighbourhoodDao;


    @Autowired
    private UserService userService;

    @Override
    public Property get(long id) {
        return propertyDao.get(id);
    }

    @Override
    public PageResponse<Property> getAll(PageRequest pageRequest) {
        if(pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 1)
            pageRequest = new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        return new PageResponse<>(pageRequest,
                                propertyDao.count(),
                                propertyDao.getAllActive(pageRequest));
    }

    @Override
    public PageResponse<Property> getByDescription(PageRequest pageRequest, String description){
        if(pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 1)
            pageRequest = new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

        return new PageResponse<>(pageRequest,
                                  propertyDao.count(),
                                  propertyDao.getPropertyByDescription(pageRequest, description));
    }

    @Override
    public PageResponse<Property> advancedSearch(PageRequest pageRequest, SearchableProperty property){
        return new PageResponse<>(pageRequest,
                                  propertyDao.totalItemsOfSearch(property),
                                  propertyDao.advancedSearch(pageRequest, property));
    }

    @Override
    public int showInterestOrReturnErrors(long propertyId, User user) {
        if (propertyId < 1 || propertyDao.get(propertyId) == null)
            return HttpURLConnection.HTTP_NOT_FOUND;
        propertyDao.showInterest(propertyId, user);
        return HttpURLConnection.HTTP_OK;
    }

    @Override
    public int undoInterestOrReturnErrors(long propertyId, User user) {
        if (propertyId < 1 || propertyDao.get(propertyId) == null)
            return HttpURLConnection.HTTP_NOT_FOUND;
        boolean wasDeleted = propertyDao.undoInterest(propertyId, user);
        if(!wasDeleted)
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        return HttpURLConnection.HTTP_OK;
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

    @Override
    public int delete(long id, User currentUser) {
        if(currentUser.getId() != id)
            return HttpURLConnection.HTTP_FORBIDDEN;
        propertyDao.delete(id);
        return HttpURLConnection.HTTP_OK;
    }

    private void checkRelatedEntitiesExist(Property property) {
        List<Image> images = new LinkedList<>(property.getImages());
        images.add(property.getMainImage());
        checkImagesExist(images);
        checkServicesExist(property.getServices());
        checkRulesExist(property.getRules());
        checkNeighbourhoodExists(property.getNeighbourhood().getId());
    }

    private void checkImagesExist(Collection<Image> images) {
        for (Image image: images) {
            if(imageDao.get(image.getId()) == null) {
                errors.add(IMAGE_NOT_EXISTS);
                return;
            }
        }
    }

    private void checkServicesExist(Collection<Service> services) {
        for (Service service: services) {
            if(serviceDao.get(service.getId()) == null) {
                errors.add(SERVICE_NOT_EXISTS);
                return;
            }
        }
    }

    private void checkRulesExist(Collection<Rule> rules) {
        for (Rule rule:rules) {
            if(ruleDao.get(rule.getId()) == null) {
                errors.add(RULE_NOT_EXISTS);
                return;
            }
        }
    }

    private void checkNeighbourhoodExists(long id) {
        if(neighbourhoodDao.get(id) == null) {
            errors.add(NEIGHBOURHOOD_NOT_EXISTS);
        }
    }

    @Override
    public Collection<Property> getInterestsOfUser(long id) {
        return propertyDao.getInterestsOfUser(id);
    }

    @Override
    public Collection<Property> getByOwnerId(long id) {
        return propertyDao.getByOwnerId(id);
    }

    @Override
    public int changeStatus(long propertyId) {
        User currentUser = userService.getCurrentlyLoggedUser();
        Property prop = propertyDao.get(propertyId);
        if(prop == null)
            return HttpURLConnection.HTTP_NOT_FOUND;
        if(prop.getOwner().getId() != currentUser.getId())
            return HttpURLConnection.HTTP_FORBIDDEN;
        propertyDao.changeStatus(propertyId);
        return HttpURLConnection.HTTP_OK;
    }

    @Override
    public int propertyCanBeShown(Property property) {
        final User user = userService.getCurrentlyLoggedUser();
        if (property == null)
            return HttpURLConnection.HTTP_NOT_FOUND;
        else if (property.getAvailability() == Availability.RENTED &&
                user != null &&
                property.getOwner().getId() != user.getId())
            return HttpURLConnection.HTTP_FORBIDDEN;
        return HttpURLConnection.HTTP_OK;
    }
}
