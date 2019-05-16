package ar.edu.itba.paw.model;

public class UserProposal {
    long id;
    long userId;
    long proposalId;

    public UserProposal(long id, long userId, long proposalId){
        this.id = id;
        this.userId = userId;
        this.proposalId = proposalId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProposalId() {
        return proposalId;
    }

    public void setProposalId(long proposalId) {
        this.proposalId = proposalId;
    }
}
