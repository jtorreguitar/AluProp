package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.University;

import java.util.Collection;

public interface UniversityService {
    University get(long id);
    Collection<University> getAll();
}
