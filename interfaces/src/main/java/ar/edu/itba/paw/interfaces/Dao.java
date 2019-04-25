package ar.edu.itba.paw.interfaces;

import java.util.Collection;

public interface Dao<T> {
    T get(long id);
    Collection<T> getAll();
}
