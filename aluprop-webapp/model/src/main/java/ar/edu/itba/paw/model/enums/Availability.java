package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Availability {
    AVAILABLE(0),
    RENTED(1);

    private int value;
    private static Map<Integer, Availability> map = new HashMap<>();

    private Availability(int value){
        this.value = value;
    }

    static {
        for (Availability availability: Availability.values()){
            map.put(availability.value, availability);
        }
    }

    public static Availability valueOf(int availability){
        return map.get(availability);
    }

    public int getValue(){
        return value;
    }

}
