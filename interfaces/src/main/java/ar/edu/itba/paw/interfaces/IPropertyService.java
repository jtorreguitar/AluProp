package ar.edu.itba.paw.interfaces;

import java.util.Collection;
import java.util.List;

import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.Property;

public interface IPropertyService {

    Property get(int id);
    Collection<Property> getAll();
	Either<Interest, List<String>> interest(int propertyId, String email, String description);
}