package com.example.mystylist.structures;

import android.util.Log;

import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Closet implements Serializable {
    private final Profile parent;
    private ArrayList<Item> items;

    public Closet(Profile parent){
        this.parent = parent;
        this.items = new ArrayList<>();
    }

    public Profile getParent() {
        return this.parent;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public Closet setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
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

    public static Closet generateDemoCloset() {
        Closet closet = new Closet(null);
        closet = new Closet(null);
        closet.addItem(new Item(EItemType.T_SHIRT, EColor.BLACK));
        closet.addItem(new Item(EItemType.BLOUSE, EColor.WHITE));
        closet.addItem(new Item(EItemType.COAT, EColor.BROWN));
        closet.addItem(new Item(EItemType.DRESS, EColor.BEIGE));
        closet.addItem(new Item(EItemType.HEELS, EColor.GREEN));
        closet.addItem(new Item(EItemType.LONG_SLEEVE_SHIRT, EColor.GREY));
        closet.addItem(new Item(EItemType.LOAFERS, EColor.DARK_BLUE));
        return closet;
    }
}
