package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.University;

import java.util.Collection;

public interface UniversityDao {
    University get(long id);
    Collection<University> getAll();
}
