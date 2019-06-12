package ar.edu.itba.paw.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum NotificationState {

    UNREAD(0),
    READ(1);

    private int value;
    private static Map<Integer, NotificationState> map = new HashMap<>();

    NotificationState(int value) {
        this.value = value;
    }

    static {
        for (NotificationState notificationState : NotificationState.values()) {
            map.put(notificationState.value, notificationState);
        }
    }

    public static NotificationState valueOf(int notificationState) {
        return map.get(notificationState);
    }

    public int getValue() {
        return value;
    }
}