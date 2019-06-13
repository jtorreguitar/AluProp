package ar.edu.itba.paw.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

public enum SearchablePropertyType {
    HOUSE(0),
    APARTMENT(1),
    LOFT(2),
    NOT_APPLICABLE(3);

    private int value;
    private static Map<Integer, SearchablePropertyType> map = new HashMap<>();

    SearchablePropertyType(int value){
        this.value = value;
    }

    static {
        for (SearchablePropertyType searchablePropertyType: SearchablePropertyType.values()){
            map.put(searchablePropertyType.value, searchablePropertyType);
        }
    }

    public static SearchablePropertyType valueOf(int searchablePropertyType){
        return map.get(searchablePropertyType);
    }

    public int getValue(){
        return value;
    }
}
