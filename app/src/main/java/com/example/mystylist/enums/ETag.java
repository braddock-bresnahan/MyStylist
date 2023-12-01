package com.example.mystylist.enums;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public enum ETag implements Serializable {

    // Flag Distribution: 0b 00000000 00000000 00000000 00000000 00444444 00003333 00002222 00000111
    // 1 = Gender
    // 2 = Season
    // 3 = Weather
    // 4 = Style

    GENDER_NEUTRAL(  "Neutral",   0b001),
    GENDER_MASCULINE("Masculine", 0b010),
    GENDER_FEMININE( "Feminine",  0b100),

    SEASON_SPRING("Spring", 0b0001 << 8),
    SEASON_SUMMER("Summer", 0b0010 << 8),
    SEASON_FALL(  "Fall",   0b0100 << 8),
    SEASON_WINTER("Winter", 0b1000 << 8),

    WEATHER_FAIR( "Fair",    0b0001 << 16),
    WEATHER_HOT(  "Hot",     0b0010 << 16),
    WEATHER_COLD( "Cold",    0b0100 << 16),
    WEATHER_RAINY("Rainy",   0b1000 << 16),

    STYLE_CASUAL(               "Casual",                0b000001 << 24),
    STYLE_SMART_CASUAL(         "Smart Casual",          0b000010 << 24),
    STYLE_BUSINESS_CASUAL(      "Business Casual",       0b000100 << 24),
    STYLE_BUSINESS_PROFESSIONAL("Business Professional", 0b001000 << 24),
    STYLE_SEMI_FORMAL(          "Semi-formal",           0b010000 << 24),
    STYLE_FORMAL(               "Formal",                0b100000 << 24);

    /**
     * A mask that encompasses all bits.
     */
    public static final long EVERYTHING_MASK = 0xffffffffffffffffL;
    /**
     * A mask that encompasses all masked bits in the Gender category.
     */
    public static final long GENDER_CATEGORY_MASK = 0b111;
    /**
     * A mask that encompasses all masked bits in the Season category.
     */
    public static final long SEASON_CATEGORY_MASK = 0b1111 << 8;
    /**
     * A mask that encompasses all masked bits in the Weather category.
     */
    public static final long WEATHER_CATEGORY_MASK = 0b1111 << 16;
    /**
     * A mask that encompasses all masked bits in the Style category.
     */
    public static final long STYLE_CATEGORY_MASK = 0b111111 << 24;

    /**
     * The text representation of the enum.
     */
    public final String text;
    /**
     * The bit mask representation of the enum.
     */
    public final long mask;

    /**
     * Constructor.
     * @param text the text representation of the enum.
     * @param mask the bit mask representation of the enum.
     */
    ETag(String text, long mask) {
        this.text = text;
        this.mask = mask;
    }

    /**
     * Returns whether any of the bits in the given masks are both active and match (the corresponding bits are both 1).
     * @param mask1 the first mask.
     * @param mask2 the second mask.
     * @return true, if the bitwise 'and' results in a non zero number; else false.
     */
    public static boolean hasMatch(long mask1, long mask2) {
        return (mask1 & mask2) != 0;
    }

    /**
     * Converts the given bit mask to an array of ETags.
     * @param mask the mask to convert to an ETag array.
     * @return an ETag array representation of the bit mask.
     */
    public static List<ETag> maskToTags(long mask) {
        List<ETag> tags = new LinkedList<>();
        for (ETag tag : values()) {
            if (hasMatch(tag.mask, mask))
                tags.add(tag);
        }
        return tags;
    }

    /**
     * Returns the mask representation of the given ETags
     * @param tags the array of ETags to convert to a mask.
     * @return a bit mask representation of the tags.
     */
    public static long tagsToMask(@NonNull List<ETag> tags) {
        long mask = 0;
        for (ETag tag : tags)
            mask |= tag.mask;
        return mask;
    }

    @NonNull
    @Override
    public String toString() { return text; }
}
