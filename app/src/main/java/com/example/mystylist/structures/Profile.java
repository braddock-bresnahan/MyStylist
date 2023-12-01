package com.example.mystylist.structures;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
    private String name;
    private Closet closet = new Closet();
    private List<Outfit> favorites = new ArrayList<>();

    public Profile(String name){
        this.name = name;
    }

    public boolean isValid() {
        return checkValidName(name);
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

    public Closet getCloset() {
        return closet;
    }
    public void setCloset(Closet closet) {
        this.closet = closet;
    }

    public List<Outfit> getFavorites() {
        return favorites;
    }
    public void updateFavorites(List<Outfit> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Profile))
            return false;

        final Profile other = (Profile) obj;
        if (other.name.compareTo(this.name) != 0)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
