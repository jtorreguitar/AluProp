package ar.edu.itba.paw.interfaces.service;

import java.util.Collection;
import java.util.List;

import ar.edu.itba.paw.model.Property;

public interface PropertyService {

    Property get(long id);
    Collection<Property> getAll();
	List<String> showInterestOrReturnErrors(long propertyId, String username);
    Property getPropertyWithRelatedEntities(long id);
}
