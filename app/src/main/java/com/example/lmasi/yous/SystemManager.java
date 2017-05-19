package com.example.lmasi.yous;

/**
 * Created by lmasi on 2017. 5. 19..
 */

public class SystemManager {

    private static boolean isLogged;

    private static String userId;

    public static boolean isLogged() {
        return isLogged;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setIsLogged(boolean isLogged) {
        SystemManager.isLogged = isLogged;

        DbResource.update("login", isLogged);
    }

    public static void setUserId(String userId) {
        SystemManager.userId = userId;

        DbResource.update_cId(userId);
    }
}
