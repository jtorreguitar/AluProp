package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Career;

import java.util.Collection;

public interface CareerService {
    Career get(long id);
    Collection<Career> getAll();
}
