package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Interest;

import java.util.stream.Stream;

public interface InterestDao {
    Stream<Interest> getAllAsStream();
}
