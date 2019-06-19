package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.dao.PropertyDao;
import ar.edu.itba.paw.interfaces.dao.ProposalDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.Proposal;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class APProposalServiceTest {

    @InjectMocks
    private APProposalService proposalService = new APProposalService();

    @Mock
    private PropertyDao propertyDao;

    @Mock
    private ProposalDao proposalDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserService userService;

    @Mock
    private Proposal mockProposal;

    @Mock
    private APNotificationService notificationService;


    @Test
    public void createValidProposalCreateNewProposalTest(){
        Proposal proposal = Factories.proposalCreator();
        long[] userIds = {};
        User loggedUser = Factories.userCreator();

        Mockito.when(userService.getCurrentlyLoggedUser()).thenReturn(loggedUser);
        Mockito.when(proposalDao.create(proposal, userIds)).thenReturn(proposal);
        Mockito.when(propertyDao.get(proposal.getProperty().getId())).thenReturn(proposal.getProperty());
        Mockito.when(userDao.get(proposal.getCreator().getId())).thenReturn(loggedUser);

        Either<Proposal, List<String>> maybeProposal = proposalService.createProposal(proposal, userIds);

        Assert.assertNotNull(maybeProposal);
        Assert.assertTrue(maybeProposal.hasValue());
        Assert.assertEquals(proposal.getId(), maybeProposal.value().getId());


    }


    @Test
    public void createInvalidProposalReturnsErrorStringTest(){
        Proposal proposal = Factories.proposalCreator();
        long[] userIds = {};
        User loggedUser = Factories.userCreator();

        Mockito.when(userService.getCurrentlyLoggedUser()).thenReturn(loggedUser);
        Mockito.when(proposalDao.create(proposal, userIds)).thenReturn(proposal);

        Either<Proposal, List<String>> maybeProposal = proposalService.createProposal(proposal, userIds);

        Assert.assertNotNull(maybeProposal);
        Assert.assertFalse(maybeProposal.hasValue());
        Assert.assertEquals(2, maybeProposal.alternative().size());


        Mockito.when(userDao.get(proposal.getCreator().getId())).thenReturn(loggedUser); //"Fix" one error

        maybeProposal = proposalService.createProposal(proposal, userIds);

        Assert.assertNotNull(maybeProposal);
        Assert.assertFalse(maybeProposal.hasValue());
        Assert.assertEquals(1, maybeProposal.alternative().size());

    }

    @Test
    public void deleteReturnsHTTPErrorWhenCreatorDifferentToLoggedUserTest(){
        User loggedUser = Factories.userCreatorWithID(100);
        Proposal proposal = Factories.proposalCreator();

        Mockito.when(proposalDao.get(proposal.getId())).thenReturn(proposal);
        Mockito.when(userService.getCurrentlyLoggedUser()).thenReturn(loggedUser);
        Mockito.when(mockProposal.getCreator()).thenReturn(Factories.userCreatorWithID(1)); //Make it different

        int httpCode = proposalService.delete(proposal.getId());

        Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, httpCode);
    }

    @Test
    public void deleteReturnsOKWhenLoggedUserEqualsProposalCreatorTest(){
        User loggedUser = Factories.userCreator();
        Proposal proposal = Factories.proposalCreator();

        Mockito.when(proposalDao.get(proposal.getId())).thenReturn(proposal);
        Mockito.when(userService.getCurrentlyLoggedUser()).thenReturn(loggedUser);
        Mockito.when(mockProposal.getCreator()).thenReturn(loggedUser);

        int httpCode = proposalService.delete(proposal.getId());

        Assert.assertEquals(HttpURLConnection.HTTP_OK, httpCode);
    }




}

