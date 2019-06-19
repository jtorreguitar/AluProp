package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ServiceDao;
import ar.edu.itba.paw.model.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

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

}

