package ar.edu.itba.paw.interfaces;

import java.util.Collection;

import ar.edu.itba.paw.model.Property;

public interface PropertyDao {

    Property get(int id);
    Collection<Property> getAll();
	boolean showInterest(int propertyId, String email, String description);
}