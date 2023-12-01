package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum EItemType implements Serializable {
    T_SHIRT(1, "Spring , Summer", "Casual", "Unisex", "Top", "T-shirt"),
    SLEEVELESS_SHIRT(2, "Spring , Summer", "Casual", "Unisex", "Top", "Sleeveless Shirt"),
    POLO(3, "Spring , Summer", "Semi-Casual", "Unisex", "Top", "Polo"),
    LONG_SLEEVE_SHIRT(4, "Fall , Winter", "Casual", "Unisex", "Top", "Long Sleeve Shirt"),
    BUTTON_DOWN_SHIRT(5, "MISSING", "MISSING", "MISSING", "MISSING", "Button Down Shirt"),  // TODO
    BLOUSE(6, "MISSING", "MISSING", "MISSING", "MISSING", "Blouse"),  // TODO
    PANTS(7, "Fall", "Casual", "Unisex", "Bottom", "Pants"),
    SHORTS(8, "Spring , Summer", "Casual", "Unisex", "Bottom", "Shorts"),
    JEANS(9, "Fall , Winter", "Casual", "Unisex", "Bottom", "Jeans"),
    LONG_SKIRT(10, "Spring", "Casual", "Female", "Bottom", "Long Skirt"),
    SHORT_SKIRT(11, "Spring , Summer", "Casual", "Female", "Bottom", "Short Skirt"),
    SUIT_JACKET(12, "MISSING", "MISSING", "MISSING", "MISSING", "Suit Jacket"),  // TODO
    JACKET(13, "Fall , Winter", "Casual", "Unisex", "Outerwear", "Jacket"),
    COAT(14, "Fall , Winter", "Casual", "Unisex", "Outerwear", "Coat"),
    WINDBREAKER(15, "Fall , Winter", "Casual", "Unisex", "Outerwear", "Windbreaker"),
    SWEATER(16, "Fall , Winter", "Casual", "Unisex", "Top", "Sweater"),
    HOODIE(17, "Fall , Winter", "Casual", "Unisex", "Top", "Hoodie"),
    DRESS(18, "Spring , Summer", "Casual", "Female", "Top", "Dress"),
    SUNDRESS(19, "MISSING", "MISSING", "MISSING", "MISSING", "Sundress"),  // TODO
    SPORTS_BRA(20, "Summer", "Sports", "Female", "Top", "Sports Bra"),
    SHORT_SOCKS(21, "Spring , Summer", "Casual", "Unisex", "Socks", "Short Socks"),
    LONG_SOCKS(22, "Spring , Summer", "Casual", "Unisex", "Socks", "Long Socks"),
    LEGGINGS(23, "Spring", "Casual", "Female", "Bottom", "Leggings"),
    SNEAKERS(24, "Spring , Summer", "Casual", "Unisex", "Shoes", "Sneakers"),
    LOAFERS(25, "Spring , Summer", "Casual", "Unisex", "Shoes", "Loafers"),
    DRESS_SHOES(26, "MISSING", "MISSING", "MISSING", "MISSING", "Dress Shoes"),  // TODO
    HEELS(27, "MISSING", "MISSING", "MISSING", "MISSING", "Heels"),  // TODO
    HIGH_HEELS(28, "MISSING", "MISSING", "MISSING", "MISSING", "High Heels"),  // TODO
    SANDALS(29, "Spring , Summer", "Casual", "Unisex", "Shoes", "Sandals");

    private static Map<Integer, EItemType> idMap;
    private static Map<String, EItemType> stringMap;
    private static boolean mapsInitialized = false;

    /**
     * The unique id of the enum. Used for more efficient storage in database
     */
    private final int id;
    private final String season;
    private final String occasion;
    private final String gender;
    private final String category;

    /**
     * The string representation of the enum.
     */
    private final String asStr;

    EItemType(int id, String season, String occasion, String gender, String category, String asStr) {
        this.id = id;
        this.season = season;
        this.occasion = occasion;
        this.gender = gender;
        this.category = category;
        this.asStr = asStr;
    }

    public int toId() {
        return id;
    }

    public static EItemType fromId(int id) {
        if (!mapsInitialized)
            initMaps();
        return idMap.get(id);
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
    public String getCategory() {
        return category;
    }

    @NonNull
    @Override
    public String toString() {
        return asStr;
    }

    public static EItemType fromString(String str) {
        if (!mapsInitialized)
            initMaps();
        return stringMap.get(str);

    }

    private static void initMaps() {
        idMap = new HashMap<>();
        stringMap = new HashMap<>();
        for (EItemType type : EItemType.values()) {
            idMap.put(type.toId(), type);
            stringMap.put(type.toString(), type);
        }
        mapsInitialized = true;
    }
}
