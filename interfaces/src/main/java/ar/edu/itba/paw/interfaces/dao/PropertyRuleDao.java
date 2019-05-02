package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.PropertyRule;

import java.util.Collection;
import java.util.stream.Stream;

public interface PropertyRuleDao {
    Collection<PropertyRule> getAll();
}
