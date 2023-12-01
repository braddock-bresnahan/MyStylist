package com.example.mystylist.structures;

public class ItemInfo {
    private String season;
    private String occasion;
    private String gender;
    private String clothingType;
    private String color;

    public ItemInfo(String season, String occasion, String gender, String clothingCategory, String color) {
        this.season = season;
        this.occasion = occasion;
        this.gender = gender;
        this.clothingType = clothingCategory;
        this.color = color;
    }

    public String getSeason() {
        return season;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getGender() {
        return gender;
    }

    public String getClothingType() {
        return clothingType;
    }

    public String getColor() { return color; }
}
