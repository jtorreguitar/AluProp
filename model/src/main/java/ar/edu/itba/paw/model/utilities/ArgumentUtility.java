package ar.edu.itba.paw.model.utilities;

import java.sql.Date;

public class ArgumentUtility {
    public static void stringIsNotNullOrEmpty(String s, String message) {
        if(s == null || s == "")
            throw new IllegalArgumentException(message);
    }

    public static void isNotNull(Object o, String s) {
        if(o == null)
            throw new IllegalArgumentException(s);
    }

    public static void isNotPositive(int i, String s){
        if(i < 1)
            throw new IllegalArgumentException(s);
    }

    public static void isNotPositive(long i, String s){
        if(i < 1)
            throw new IllegalArgumentException(s);
    }
}
