package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;

public interface PropertyDao extends Dao<Property>{
	boolean showInterest(long propertyId, User user);
	Property getPropertyWithRelatedEntities(long id);
    Property create(Property property);
}
