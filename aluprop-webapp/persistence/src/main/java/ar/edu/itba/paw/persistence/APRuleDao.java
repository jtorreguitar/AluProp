package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RuleDao;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Rule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class APRuleDao implements RuleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Rule get(long id) {
        return entityManager.find(Rule.class, id);
    }

    @Override
    public Collection<Rule> getAll() {
        return entityManager.createQuery("FROM Rule", Rule.class).getResultList();
    }

    @Transactional
    @Override
    public Collection<Rule> getRulesOfProperty(long propertyId) {
        Property property = entityManager.find(Property.class, propertyId);
        property.getRules().isEmpty();
        return property.getRules();
    }
}
