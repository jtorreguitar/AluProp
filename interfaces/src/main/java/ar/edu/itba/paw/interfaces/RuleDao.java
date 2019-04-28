package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Rule;

import java.util.stream.Stream;

public interface RuleDao {
    Stream<Rule> getAllAsStream();
}
