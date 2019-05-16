package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.interfaces.PageRequest;

import java.util.Collection;

public interface Dao<T> {
    T get(long id);
    Collection<T> getAll(PageRequest pageRequest);
}
