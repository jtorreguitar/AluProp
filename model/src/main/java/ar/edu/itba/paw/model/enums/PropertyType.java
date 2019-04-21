package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum PropertyType {
    HOUSE(0),
    APARTMENT(1),
    LOFT(2),
    MOTEL(3);

    private int value;
    private static Map<Integer, PropertyType> map = new HashMap<>();

    private PropertyType(int value) {
        this.value = value;
    }

    static {
        for (PropertyType property : PropertyType.values()) {
            map.put(property.value, property);
        }
    }

    public static PropertyType valueOf(int property) {
        return map.get(property);
    }

    public int getValue() {
        return value;
    }
}