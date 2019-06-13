package ar.edu.itba.paw.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

public enum SearchablePrivacyLevel {
    INDIVIDUAL(0),
    SHARED(1),
    NOT_APPLICABLE(2);

    private int value;
    private static Map<Integer, SearchablePrivacyLevel> map = new HashMap<>();

    SearchablePrivacyLevel(int value){
        this.value = value;
    }

    static {
        for (SearchablePrivacyLevel searchablePrivacyLevel: SearchablePrivacyLevel.values()){
            map.put(searchablePrivacyLevel.value, searchablePrivacyLevel);
        }
    }

    public static SearchablePrivacyLevel valueOf(int searchablePrivacyLevel){
        return map.get(searchablePrivacyLevel);
    }

    public int getValue(){
        return value;
    }
}
