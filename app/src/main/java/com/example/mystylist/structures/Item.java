package com.example.mystylist.structures;

import androidx.annotation.NonNull;

import com.example.mystylist.R;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;

import java.io.Serializable;

public class Item implements Serializable, Comparable<Item> {
    public int drawable_id;
    public EItemType type;
    public EColor color;

    public Item(EItemType type, EColor color) {
        this.type = type;
        this.color = color;

        // Search for the image of the item type of the particular color
        this.drawable_id = R.drawable.ic_launcher_background;
    }

    public int getDrawableId() {
        return drawable_id;
    }
    public Item setDrawableId(int drawableId) {
        this.drawable_id = drawableId;
        return this;
    }

    public EItemType getType() {
        return type;
    }
    public Item setType(EItemType type) {
        this.type = type;
        return this;
    }

    public EColor getColor() {
        return color;
    }
    public Item setColor(EColor color) {
        this.color = color;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + type.toString() + ", " + color.toString() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item))
            return false;

        final Item other = (Item) obj;
        if (other.type != this.type)
            return false;
        if (other.color != this.color)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 23;  // Arbitrary prime number
        int hash = 5;  // Arbitrary starting position

        hash = PRIME * hash + type.hashCode();
        hash = PRIME * hash + color.hashCode();

        return hash;
    }

    @Override
    public int compareTo(Item other) {
        int myValue = (this.type.toId() << 16) + (this.color.toInt());
        int otherValue = (other.type.toId() << 16) + (other.color.toInt());
        return myValue - otherValue;
    }
}
