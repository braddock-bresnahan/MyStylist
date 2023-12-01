package com.example.mystylist.structures;

import java.io.Serializable;

public class Profile implements Serializable {
    private final String name;
    public String getName(){
        return this.name;
    }
    public Profile(String name){
        this.name = name;
    }

    public boolean hasValidName() {
        return name != null && name.length() > 0;
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
