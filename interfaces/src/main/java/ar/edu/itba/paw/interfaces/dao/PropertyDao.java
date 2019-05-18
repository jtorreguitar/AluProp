package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;

import java.util.Collection;

public interface PropertyDao extends Dao<Property>{
	boolean showInterest(long propertyId, User user);
	boolean undoInterest(long propertyId, User user);

    Property getPropertyWithRelatedEntities(long id);
    Property create(Property property);
    Collection<Property> getInterestsOfUser(long id);
    PageResponse<Property> getInterestsOfUserPaged(long id, PageRequest pageRequest);
    Long count();
}
