package ar.edu.itba.paw.model;

import java.util.Collection;
import java.util.LinkedList;

public class Proposal {
    private long id;
    private long propertyId;
    private long creatorId;
    private Collection<User> users;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPropertyId() {return propertyId;}
    public void setPropertyId(long propertyId) { this.propertyId = propertyId; }

    public long getCreatorId() { return creatorId; }
    public void setCreatorId(long creatorId) { this.creatorId = creatorId; }

    public Collection<User> getUsers() { return users; }
    public void setUsers(Collection<User> users) { this.users = users; }

    public static class Builder {
        private Proposal proposal;

        public Builder() {this.proposal = new Proposal();}

        public Builder withId(long id){proposal.id = id;return this;}

        public Builder withPropertyId(long propertyId){proposal.propertyId = propertyId;return this;}

        public Builder withCreatorId(long creatorId){proposal.creatorId = creatorId;return this;}

        public Builder withUsers(Collection<User> users){proposal.users = users;return this;}

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
