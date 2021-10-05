package com.project.setech.model;

import java.util.List;

public abstract class Item implements IItem {
    private final String name;
    private final String description;
    private final List<String> images;
    private final float price;

    public Item(String name, String description, List<String> images, float price) {
        this.name = name;
        this.description = description;
        this.images = images;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getImages() {
        return images;
    }
    public float getPrice() {
        return price;
    }
}
