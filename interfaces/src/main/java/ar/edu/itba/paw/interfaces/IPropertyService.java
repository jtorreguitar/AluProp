package ar.edu.itba.paw.interfaces;

import java.util.Collection;

import ar.edu.itba.paw.model.Property;

public interface IPropertyService {

    Property get(int id);
    Collection<Property> getAll();
}