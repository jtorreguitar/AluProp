package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

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
        return entityManager.createQuery("from Career c", Career.class).getResultList();
    }
}
