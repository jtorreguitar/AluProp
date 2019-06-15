package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProposalState {
    PENDING(0),
    ACCEPTED(1),
    DECLINED(2),
    INACTIVE(3);

    private int value;
    private static Map<Integer, ProposalState> map = new HashMap<>();

    private ProposalState(int value){
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