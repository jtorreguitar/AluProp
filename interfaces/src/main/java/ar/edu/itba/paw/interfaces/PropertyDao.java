package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.dao.Dao;
import ar.edu.itba.paw.model.Property;

public interface PropertyDao extends Dao<Property> {

	boolean showInterest(int propertyId, String email, String description);
    Property getPropertyWithRelatedEntities(long id);
}
