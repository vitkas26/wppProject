package com.example.vpp_android.domain.auth_classes;

public class Authorization {
    private String username;
    private String password;
    private String token;
    private int user_id;
    private int fucking_looser;

    public Authorization(int user_id, String token) {
        this.user_id = user_id;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return user_id;
    }
}
