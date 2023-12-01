package com.example.mystylist.structures;

import androidx.annotation.NonNull;

public class Account {
    private final String username;
    private final String password;
    private final String email;
    private final String name;

    public Account(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "{username: " + username + ", email: " + email + ", name: " + name + "}";
    }
}
