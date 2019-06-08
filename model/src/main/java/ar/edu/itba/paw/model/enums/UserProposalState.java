package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserProposalState {

    ACCEPTED(0),
    REJECTED(1),
    PENDING(2);

    private int value;
    private static Map<Integer, UserProposalState> map = new HashMap<>();

    UserProposalState(int value) {
        this.value = value;
    }

    static {
        for (UserProposalState proposalState : UserProposalState.values()) {
            map.put(proposalState.value, proposalState);
        }
    }

    public static UserProposalState valueOf(int proposalState) {
        return map.get(proposalState);
    }

    public int getValue() {
        return value;
    }
}
