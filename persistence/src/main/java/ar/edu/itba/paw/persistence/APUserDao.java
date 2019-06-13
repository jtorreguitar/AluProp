package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.persistence.utilities.QueryUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class APUserDao implements UserDao {

    private static final String INTEREST_COUNT_BY_USER_AND_PROPERTY = "SELECT COUNT(i.id) FROM Interest i WHERE i.user.id = :userId AND i.property.id = :propertyId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User getWithRelatedEntities(long id){
        User user = entityManager.find(User.class, id);
        initializeRelatedEntities(user);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        /*
        TypedQuery<User> query = entityManager.createQuery("FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();*/
        try {
            return entityManager.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch(NoResultException e){
            return null; //No user with this email exists
        }
    }

    private void initializeRelatedEntities(User user) {
        user.getUserProposals().isEmpty();
        user.getInterestedProperties().isEmpty();
        user.getNotifications().isEmpty();
        user.getOwnedProperties().isEmpty();
        user.getUserProposals().forEach(up -> up.getProposal().getId());
    }

    @Override
    public Collection<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User create(User user) {
        entityManager.merge(user);
        return user;
    }

    @Transactional
    @Override
    public User getUserWithRelatedEntitiesByEmail(String email) {
        User user = getByEmail(email);
        initializeRelatedEntities(user);
        return user;
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return !entityManager.createQuery("FROM User u WHERE u.email = :email").getResultList().isEmpty();
    }

    @Override
    public Collection<User> getUsersInterestedInProperty(long id, PageRequest pageRequest) {
        TypedQuery<Interest> query = entityManager.createQuery("FROM Interest i WHERE i.property.id = :propertyId", Interest.class);
        query.setParameter("propertyId", id);
        Collection<Interest> interests = QueryUtility.makePagedQuery(query, pageRequest).getResultList();
        return interests.stream().map(Interest::getUser).collect(Collectors.toList());
    }

    @Override
    public boolean isUserInterestedInProperty(long userId, long propertyId){
        TypedQuery<Long> query = entityManager
                                    .createQuery(INTEREST_COUNT_BY_USER_AND_PROPERTY, Long.class);
        query.setParameter("userId", userId);
        query.setParameter("propertyId", propertyId);
        return query.getSingleResult() > 0;
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(u.id) FROM User u", Long.class).getSingleResult();
    }
}
