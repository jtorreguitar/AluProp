package ar.edu.itba.paw.interfaces.dao;

import java.util.Collection;

public interface Dao<T> {
    T get(long id);
    Collection<T> getAll();
}
