package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ProposalState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Proposal {
    private long id;
    private long propertyId;
    private long creatorId;
    private List<User> users;
    private List<Integer> invitedUserStates;
    private ProposalState state;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPropertyId() {return propertyId;}
    public void setPropertyId(long propertyId) { this.propertyId = propertyId; }

    public long getCreatorId() { return creatorId; }
    public void setCreatorId(long creatorId) { this.creatorId = creatorId; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public List<Integer> getInvitedUserStates() {
        return invitedUserStates;
    }

    public void setInvitedUserStates(List<Integer> invitedUserStates) {
        this.invitedUserStates = invitedUserStates;
    }

    public ProposalState getState() { return state;}
    public void setState(ProposalState state) { this.state = state; }

    public boolean isCompletelyAccepted(){
        for (Integer state: invitedUserStates)
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

        public Builder withUsers(List<User> users){proposal.users = users;return this;}

        public Builder withInvitedUserStates(List<Integer> userStates){proposal.invitedUserStates = userStates;return this;}

        public Builder fromProposal(Proposal proposal){
            this.proposal.id = proposal.id;
            this.proposal.creatorId = proposal.creatorId;
            this.proposal.propertyId = proposal.propertyId;
            this.proposal.users = proposal.users;
            return this;
        }

        public Proposal build(){
            initializeLists();return proposal;
        }

        private void initializeLists() {if(this.proposal.users == null) this.proposal.users = new LinkedList<>(); }

    }
}
