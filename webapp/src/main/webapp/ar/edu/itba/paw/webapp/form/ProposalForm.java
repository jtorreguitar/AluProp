package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Length;

public class ProposalForm {
    private long[] invitedUsersIds;

    public long[] getInvitedUsersIds() {
        return invitedUsersIds;
    }

    public void setInvitedUsersIds(long[] invitedUsersIds) {
        this.invitedUsersIds = invitedUsersIds;
    }

}
