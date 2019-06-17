package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.enums.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class APImageDao implements ImageDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Image get(long id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    public Collection<Image> getByProperty(long propertyId) {
        final TypedQuery<Image> query = entityManager.createQuery("from Image i where i.property.id = :propertyId", Image.class);
        query.setParameter("propertyId", propertyId);
        return query.getResultList();
    }

    @Override
    public void addProperty(long id, long propertyId) {
        Image i = entityManager.find(Image.class, id);
        Property p = entityManager.find(Property.class, propertyId);
        i.setProperty(p);
        entityManager.persist(i);
    }

    @Override
    @Transactional
    public long create(byte[] image) {
        Image i = new Image(image);
        entityManager.persist(i);
        return i.getId();
    }
}
