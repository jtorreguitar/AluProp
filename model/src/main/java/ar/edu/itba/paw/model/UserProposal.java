package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ProposalState;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proposalId")
    private Proposal proposal;

    @Enumerated(EnumType.ORDINAL)
    private UserProposalState state;

    /* package */ UserProposal() { }

    public UserProposal(long id){
        this.id = id;
        this.state = UserProposalState.PENDING;
    }

    public UserProposal(User user, Proposal proposal) {
        this.user = user;
        this.proposal = proposal;
        this.state = UserProposalState.PENDING;
    }

    public static UserProposal fromUser(User user) {
        UserProposal prop = new UserProposal();
        prop.user = user;
        prop.state = UserProposalState.PENDING;
        return prop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserProposalState getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setState(UserProposalState state) {
        this.state = state;
    }
}
