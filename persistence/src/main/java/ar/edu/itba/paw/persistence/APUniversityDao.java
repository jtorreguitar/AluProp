package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.model.University;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class APUniversityDao implements UniversityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public University get(long id) {
        return entityManager.find(University.class, id);
    }

    @Override
    public Collection<University> getAll() {
        return entityManager.createQuery("FROM University", University.class).getResultList();
    }
}
