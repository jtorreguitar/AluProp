package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.SearchableProperty;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;

import java.util.Collection;

public interface PropertyDao {
    Property get(long id);
    Collection<Property> getAllActive(PageRequest pageRequest);
	void showInterest(long propertyId, User user);
	boolean undoInterest(long propertyId, User user);
    Property getPropertyWithRelatedEntities(long id);
    Collection<Property> getPropertyByDescription(PageRequest pageRequest, String description);
    Property create(Property property);
    void delete(long id);
    Collection<Property> getInterestsOfUser(long id);
    Collection<Property> getByOwnerId(long id);
    Collection<Property> getInterestsOfUserPaged(long id, PageRequest pageRequest);
    Long count();
    Collection<Property> advancedSearch(PageRequest pageRequest, SearchableProperty property);
    void changeStatus(long propertyId);
}
