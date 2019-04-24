package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    private int value;
    private static Map<Integer, Gender> map = new HashMap<>();

    private Gender(int value) {
        this.value = value;
    }

    static {
        for (Gender gender : Gender.values()) {
            map.put(gender.value, gender);
        }
    }

    public static Gender valueOf(int gender) {
        return map.get(gender);
    }

    public int getValue() {
        return value;
    }
}
