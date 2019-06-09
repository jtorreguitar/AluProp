package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.NeighbourhoodDao;
import ar.edu.itba.paw.model.Neighbourhood;
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
public class APNeighbourhoodDao implements NeighbourhoodDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Neighbourhood get(long id) {
        return entityManager.find(Neighbourhood.class, id);
    }

    @Override
    public Collection<Neighbourhood> getAll() {
        return entityManager.createQuery("FROM Neighbourhood", Neighbourhood.class).getResultList();
    }
}
