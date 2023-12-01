package com.example.mystylist.structures;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Account {
    private String username;
    private String password;
    private String email;
    private String name;
    private List<Profile> profiles = new ArrayList<>();
    private int activeProfileIndex = 0;

    public Account(@NonNull String username, @NonNull String password, @NonNull String email, @NonNull String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public boolean isValid() {
        return checkValidUsername(username) && checkValidPassword(password) && checkValidEmail(email) && checkValidName(name);
    }

    public String getUsername() {
        return username;
    }
    public boolean setUsername(@NonNull String username) {
        if (checkValidUsername(username)) {
            this.username = username;
            return true;
        }
        return false;
    }
    public static boolean checkValidUsername(@NonNull String username) {
        return !username.isEmpty();
    }

    public String getPassword() {
        return password;
    }
    public boolean setPassword(@NonNull String password) {
        if (checkValidPassword(password)) {
            this.password = password;
            return true;
        }
        return false;
    }
    public static boolean checkValidPassword(@NonNull String password) {
        return !password.isEmpty();
    }

    public String getEmail() {
        return email;
    }
    public boolean setEmail(@NonNull String email) {
        if (checkValidEmail(email)) {
            this.email = email;
            return true;
        }
        return false;
    }
    public static boolean checkValidEmail(@NonNull String email) {
        return !email.isEmpty();
    }

    public String getName() {
        return name;
    }
    public boolean setName(@NonNull String name) {
        if (checkValidName(name)) {
            this.name = name;
            return true;
        }
        return false;
    }
    public static boolean checkValidName(@NonNull String name) {
        return !name.isEmpty();
    }

    public List<Profile> getProfiles() {
        return profiles;
    }
    public void updateProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Profile getActiveProfile() {
        return profiles.get(activeProfileIndex);
    }
    public Profile setActiveProfile(@NonNull String name) {
        return setActiveProfile(profiles.indexOf(new Profile(name)));
    }
    public Profile setActiveProfile(int index) {
        if (index >= 0 && index < profiles.size()) {
            activeProfileIndex = index;
            return getActiveProfile();
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{username: " + username + ", email: " + email + ", name: " + name + "}";
    }
}
