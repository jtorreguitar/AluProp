package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Interest;

import java.util.Collection;
import java.util.stream.Stream;

public interface InterestDao {
    Collection<Interest> getAll();
}
