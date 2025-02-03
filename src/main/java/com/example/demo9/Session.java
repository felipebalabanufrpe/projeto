package com.example.demo9;

public class Session {
    private static String loggedInUser;

    public static void setLoggedInUser(String user) {
        loggedInUser = user;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }
}