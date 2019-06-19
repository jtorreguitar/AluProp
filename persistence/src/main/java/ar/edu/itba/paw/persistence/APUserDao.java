package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.Paginator;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Interest;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.APPaginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@Transactional
public class APUserDao implements UserDao {

    @Autowired
    private Paginator paginator;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getWithRelatedEntities(long id){
        User user = entityManager.find(User.class, id);
        initializeRelatedEntities(user);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        try {
            return entityManager.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch(NoResultException e){
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
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getUserWithRelatedEntitiesByEmail(String email) {
        User user = getByEmail(email);
        if (user == null)
            return null;
        initializeRelatedEntities(user);
        return user;
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return !entityManager.createQuery("FROM User u WHERE u.email = :email")
                            .setParameter("email", email)
                            .getResultList()
                            .isEmpty();
    }

    @Override
    public Collection<User> getUsersInterestedInProperty(long id, PageRequest pageRequest) {
        TypedQuery<Interest> query = entityManager.createQuery("FROM Interest i WHERE i.property.id = :propertyId", Interest.class);
        query.setParameter("propertyId", id);
        Collection<Interest> interests = paginator.makePagedQuery(query, pageRequest).getResultList();
        return interests.stream().map(Interest::getUser).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(u.id) FROM User u", Long.class).getSingleResult();
    }
}
