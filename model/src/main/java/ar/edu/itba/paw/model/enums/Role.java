package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ROLE_GUEST(0),
    ROLE_HOST(1);

    private int value;
    private static Map<Integer, Role> map = new HashMap<>();

    private Role(int value) {
        this.value = value;
    }

    static {
        for (Role role : Role.values()) {
            map.put(role.value, role);
        }
    }

    public static Role valueOf(int role) {
        return map.get(role);
    }

    public int getValue() {
        return value;
    }
}
