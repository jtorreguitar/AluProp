package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.UserProposalState;

import javax.persistence.*;

@Entity
@Table(name = "userProposals")
public class UserProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userProposals_id_seq")
    @SequenceGenerator(sequenceName = "userProposals_id_seq", name = "userProposals_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Transient
    private long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Transient
    private long proposalId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proposalId")
    private Proposal proposal;

    @Enumerated(EnumType.ORDINAL)
    private UserProposalState state;

    /* package */ UserProposal() { }

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

    public UserProposalState getState() {
        return state;
    }
}
