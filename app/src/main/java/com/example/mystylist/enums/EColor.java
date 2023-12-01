package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum EColor implements Serializable {
    PINK("Pink", 0xfff9e6e6),
    RED("Red", 0xffcf202a),
    ORANGE("Orange", 0xffff9900),
    BEIGE("Beige", 0xffc9ad93),
    YELLOW("Yellow", 0xffffd700),
    GREEN("Green", 0xff4e9b47),
    LIGHT_BLUE("Light Blue", 0xff8fd0ca),
    DARK_BLUE("Dark Blue", 0xff0f4c81),
    PURPLE("Purple", 0xff6f295b),
    BROWN("Brown", 0xff5d4a44),
    WHITE("White", 0xfff8f9fa),
    GREY("Grey", 0xff787470),
    BLACK("Black", 0xff333333);

    private static Map<String, EColor> strMap;
    private static Map<Integer, EColor> intMap;
    private static boolean mapsInitialized = false;

    private final String asStr;
    private final int asInt;

    private EColor(String asText, int asInt) {
        this.asStr = asText;
        this.asInt = asInt;
    }

    @NonNull
    @Override
    public String toString() {
        return asStr;
    }

    public static EColor fromString(String str) {
        if (!mapsInitialized)
            initMaps();
        return strMap.get(str);
    }

    public int toInt() {
        return asInt;
    }

    public static EColor fromInt(int i) {
        if (!mapsInitialized)
            initMaps();
        return intMap.get(i);
    }

    private static void initMaps() {
        strMap = new HashMap<>();
        intMap = new HashMap<>();
        for (EColor color : EColor.values()) {
            strMap.put(color.toString(), color);
            intMap.put(color.toInt(), color);
        }
        mapsInitialized = true;
    }
}
