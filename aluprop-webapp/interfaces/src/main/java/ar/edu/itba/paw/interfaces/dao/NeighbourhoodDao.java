package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Neighbourhood;

import java.util.Collection;

public interface NeighbourhoodDao {
    Neighbourhood get(long id);
    Collection<Neighbourhood> getAll();
}
