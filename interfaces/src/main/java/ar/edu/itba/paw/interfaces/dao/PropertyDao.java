package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;

public interface PropertyDao extends Dao<Property>{

	boolean showInterest(int propertyId, User user);
}
