package com.htwsaar.zuse.util;

import java.sql.Timestamp;
import java.util.UUID;

public class Util {
    /**
     * Method to check for empty or null String
     *
     * @param string String to check
     * @param msg    Message send with the Exception if String was empty
     * @throws IllegalArgumentException Thrown when String is null or Empty
     */
    public static void checkForEmpty(String string, String msg) {
        if ( string.strip().length() <= 0 ) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * checks if a condition is not met and throws an exception
     * @param condition:
     * @param msg: Feedback if a condition is not met
     */
    public static void check(boolean condition, String msg){
        if(!condition){
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Function that returns timestamp of current time conventional for database
     * @return current sql.Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Generate uuid string that can be used to avoid unique constraint issues
     * @return uuid string
     */
    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
