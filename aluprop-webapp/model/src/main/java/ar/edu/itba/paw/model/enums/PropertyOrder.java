package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum PropertyOrder {

    NEWEST(0),
    CAPACITY(1),
    CAPACITY_DESC(2),
    PRICE(3),
    PRICE_DESC(4),
    BUDGET(5),
    BUDGET_DESC(6);

    private int value;
    private static Map<Integer, PropertyOrder> map = new HashMap<>();

    PropertyOrder(int value) {
        this.value = value;
    }

    static {
        for (PropertyOrder propertyOrder : PropertyOrder.values()) {
            map.put(propertyOrder.value, propertyOrder);
        }
    }

    public static PropertyOrder valueOf(int propertyOrder) {
        return map.get(propertyOrder);
    }

    public int getValue() {
        return value;
    }
}
