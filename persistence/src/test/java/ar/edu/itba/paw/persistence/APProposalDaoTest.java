package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APProposalDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProposalDao proposalDao;

    /*
    @Test
    @Transactional
    @Rollback
    public void deleteProposalDeletesProposalFromDatabaseTest(){
        Long previousProposalCount = entityManager.createQuery("SELECT Count(p.id) FROM Proposal p WHERE p.id=1", Long.class)
                                     .getSingleResult();

        proposalDao.delete(1);

        Long newProposalCount = entityManager.createQuery("SELECT Count(p.id) FROM Proposal p WHERE p.id=1", Long.class)
                                .getSingleResult();


        Assert.assertEquals(previousProposalCount-1, (long) newProposalCount);




    }
    */

    /*
    @Test
    public void getAllProposalForUserIdReturnsProposalsOfUserTest(){
        User creator = entityManager.find(User.class, (long) 1);
        Long expectedCount = entityManager.createQuery("SELECT Count(p.id) FROM Proposal p WHERE p.creator=:creator", Long.class)
                             .setParameter("creator", creator)
                             .getSingleResult();

        Long realCount = (long) proposalDao.getAllProposalForUserId((long)1).size();

        Assert.assertEquals(expectedCount, realCount);
    }
    */
}
