package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.model.Career;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class APCareerDao implements CareerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Career get(long id) {
        return entityManager.find(Career.class, id);
    }

    @Override
    public Collection<Career> getAll() {
        return entityManager.createQuery("FROM Career c", Career.class).getResultList();
    }
}
