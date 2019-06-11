package ar.edu.itba.paw.model;

import javax.persistence.*;
import ar.edu.itba.paw.model.enums.ProposalState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proposals_id_seq")
    @SequenceGenerator(sequenceName = "proposals_id_seq", name = "proposals_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Transient
    private long propertyId;

    @Transient
    private long creatorId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposalId")
    private Collection<UserProposal> userProposals;

    private ProposalState state;

    /* package */ Proposal() { }

    public long getId() { return id; }

    public long getPropertyId() { return propertyId; }

    public long getCreatorId() { return creatorId; }

    public Collection<UserProposal> getUserProposals() { return userProposals; }

    public Collection<User> getUsers() { return userProposals.stream().map(up -> up.getUser()).collect(Collectors.toList()); }

    public List<Integer> getInvitedUserStates() {
        return userProposals.stream().map(up -> up.getState().getValue()).collect(Collectors.toList());
    }

    public ProposalState getState() { return state;}
    public void setState(ProposalState state) { this.state = state; }

    public boolean isCompletelyAccepted(){
        for (Integer state: getInvitedUserStates())
            if (state != 1)
                return false;
        return true;
    }

    public static class Builder {
        private Proposal proposal;

        public Builder() {this.proposal = new Proposal();}

        public Builder withId(long id){proposal.id = id;return this;}

        public Builder withPropertyId(long propertyId){proposal.propertyId = propertyId;return this;}

        public Builder withCreatorId(long creatorId){proposal.creatorId = creatorId;return this;}

        public Builder withUserProposals(List<UserProposal> userProposals){proposal.userProposals = userProposals;return this;}

        public Builder fromProposal(Proposal proposal){
            this.proposal.id = proposal.id;
            this.proposal.creatorId = proposal.creatorId;
            this.proposal.propertyId = proposal.propertyId;
            this.proposal.userProposals = proposal.userProposals;
            return this;
        }

        public Proposal build(){
            initializeLists();return proposal;
        }

        private void initializeLists() {if(this.proposal.userProposals == null) this.proposal.userProposals = new LinkedList<>(); }

    }
}
