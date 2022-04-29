package com.example.vpp_android.domain.savingdata_class;

public class Account {
    private static final String FILE = "Account";
    private static final String IN_SYSTEM = "in_system";
    private static final String USER_ID = "user_id";
    private static final String USER_TOKEN = "user_token";
    private static final String USER_NAME = "user_name";

    public static String getFILE() {
        return FILE;
    }

    public static String getInSystem() {
        return IN_SYSTEM;
    }


    public static String getUserId() {
        return USER_ID;
    }

    public static String getUserToken() {
        return USER_TOKEN;
    }

    public static String getUserName() {
        return USER_NAME;
    }
}
