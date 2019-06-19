package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.ProposalState;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class APProposalDaoTest {

    private static final long PROPOSAL_ID = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProposalDao proposalDao;


    @Test
    @Transactional
    @Rollback
    public void deleteProposalDeletesProposalFromDatabaseTest(){
        ProposalState previousProposalState = entityManager.createQuery("SELECT p.state FROM Proposal p WHERE p.id = :id", ProposalState.class)
                                        .setParameter("id", PROPOSAL_ID)
                                        .getSingleResult();

        proposalDao.dropProposal(PROPOSAL_ID);

        ProposalState proposalState = entityManager.createQuery("SELECT p.state FROM Proposal p WHERE p.id = :id", ProposalState.class)
                                        .setParameter("id", PROPOSAL_ID)
                                        .getSingleResult();


        Assert.assertNotEquals(previousProposalState, proposalState);
        Assert.assertEquals(ProposalState.DROPPED, proposalState);
    }

    @Test
    public void getAllProposalForUserIdReturnsProposalsOfUserTest(){
        Long expectedCount = entityManager.createQuery("SELECT Count(p.id) FROM UserProposal p WHERE p.user.id = 1", Long.class)
                             .getSingleResult();

        Long realCount = (long) proposalDao.getAllProposalForUserId((long) 1).size();

        Assert.assertEquals(expectedCount, realCount);
    }
}
