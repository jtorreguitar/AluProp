package ar.edu.itba.paw.interfaces;

import java.util.Collection;

import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;

public interface IPropertyDao {

    Property get(int id);
    Collection<Property> getAll();
	Interest interest(int propertyId, String email, String description);
}