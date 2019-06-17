package ar.edu.itba.paw.model;

import javax.persistence.*;
import ar.edu.itba.paw.model.enums.ProposalState;
import ar.edu.itba.paw.model.enums.UserProposalState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proposals_id_seq")
    @SequenceGenerator(sequenceName = "proposals_id_seq", name = "proposals_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "proposalId")
    private Collection<UserProposal> userProposals;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propertyId")
    private Property property;

    @Enumerated(EnumType.STRING)
    private ProposalState state;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creatorId")
    private User creator;

    /* package */ Proposal() { }

    public long getId() { return id; }

    public Collection<UserProposal> getUserProposals() { return userProposals; }

    public Collection<User> getUsers() {
        return userProposals.stream().map(up -> up.getUser()).collect(Collectors.toList());
    }

    public Collection<User> getUsersWithoutCreator(long creatorId){
        return userProposals.stream().map(up -> {
            if(up.getUser().getId() != creatorId)
                return up.getUser();
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Integer> getUserStates(long ownerId) {
        return userProposals.stream().map(up -> {
            if(up.getUser().getId() != ownerId)
                return up.getState().getValue();
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Property getProperty() {
        return property;
    }

    public ProposalState getState() { return state;}
    public void setState(ProposalState state) { this.state = state; }

    public User getCreator() {
        return creator;
    }

    public boolean isCompletelyAccepted(long ownerId) {
        for(UserProposal up : getUserProposals())
            if(up.getId() != ownerId && up.getState() != UserProposalState.ACCEPTED)
                return false;
        return true;
    }


    public static class Builder {
        private Proposal proposal;

        public Builder() {this.proposal = new Proposal();}

        public Builder withId(long id){proposal.id = id;return this;}

        public Builder withUserProposals(List<UserProposal> userProposals){proposal.userProposals = userProposals;return this;}

        public Builder fromProposal(Proposal proposal){
            this.proposal.id = proposal.id;
            this.proposal.userProposals = proposal.userProposals;
            return this;
        }

        public Proposal build(){
            initializeLists();return proposal;
        }

        private void initializeLists() {if(this.proposal.userProposals == null) this.proposal.userProposals = new LinkedList<>(); }

        public Builder withCreator(User user) {
            proposal.creator = user;
            return this;
        }

        public Builder withProperty(Property property) {
            proposal.property = property;
            return this;
        }

        public Builder withState(ProposalState state) {
            proposal.state = state;
            return this;
        }
    }
}
