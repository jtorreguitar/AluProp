package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ServiceDao;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class APServiceDao implements ServiceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Service get(long id) {
        return entityManager.find(Service.class, id);
    }

    @Override
    public Collection<Service> getAll() {
        return entityManager.createQuery("FROM Service", Service.class).getResultList();
    }

    @Override
    public Collection<Service> getServicesOfProperty(long propertyId) {
        Property property = entityManager.find(Property.class, propertyId);
        property.getServices().isEmpty();
        return property.getServices();
    }

}

