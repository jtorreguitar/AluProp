package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Rule;

import java.util.Collection;

public interface RuleDao {
    Collection<Rule> getAll();
}
