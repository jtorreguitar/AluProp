package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProposalState {
    PENDING(0),
    SENT(1),
    ACCEPTED(2),
    DECLINED(3),
    CANCELED(4),
    DROPPED(5);

    private int value;
    private static Map<Integer, ProposalState> map = new HashMap<>();

    ProposalState(int value){
        this.value = value;
    }

    static {
        for (ProposalState proposalState: ProposalState.values()){
            map.put(proposalState.value, proposalState);
        }
    }

    public static ProposalState valueOf(int proposalState){
        return map.get(proposalState);
    }

    public int getValue(){
        return value;
    }

}
