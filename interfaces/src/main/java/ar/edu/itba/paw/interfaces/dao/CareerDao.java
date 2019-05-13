package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Career;

import java.util.Collection;

public interface CareerDao {
    Career get(long id);
    Collection<Career> getAll();
}
