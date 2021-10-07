package com.project.setech.model;

import java.util.List;

public abstract class Item implements IItem {
    private final String name;
    private final List<String> images;
    private final float price;

    public Item(String name, List<String> images, float price) {
        this.name = name;
        this.images = images;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public List<String> getImages() {
        return images;
    }
    public float getPrice() {
        return price;
    }
}
