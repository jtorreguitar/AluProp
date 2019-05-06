package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Service;

import java.util.Collection;

public interface ServiceDao {
    Collection<Service> getAll();
}
