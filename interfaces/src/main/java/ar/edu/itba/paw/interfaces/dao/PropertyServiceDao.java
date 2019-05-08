package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.PropertyService;

import java.util.Collection;

public interface PropertyServiceDao {
    Collection<PropertyService> getAll();
    void create(long serviceId, long propertyId);
}
