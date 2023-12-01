package com.example.mystylist.structures;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Closet implements Serializable {
    private ArrayList<Item> items = new ArrayList<>();

    public Closet() {}

    public List<Item> getItems() {
        return this.items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getItemCount() {
        return this.items.size();
    }

    public boolean inCloset(Item item) {
        return this.items.contains(item);
    }

    public Item addItem(Item new_item) {
        // Check if item is already in closet
        if (this.items.contains(new_item))
            return null;
        // else, add item to the closet
        this.items.add(new_item);
        return new_item;
    }

    public Item addItem(int index, Item new_item) {
        // Check if item is already in closet
        if (this.items.contains(new_item))
            return null;
        // else, add item to the closet
        this.items.add(index, new_item);
        return new_item;
    }

    public Item removeItem(Item item) {
        if (items.remove(item))
            return item;
        else
            return null;
    }
    public Item removeItemAt(int index) {
        return items.remove(index);
    }

    public void clearItems() {
        this.items.clear();
    }

    public Item getItemAt(int index) {
        return this.items.get(index);
    }
}
