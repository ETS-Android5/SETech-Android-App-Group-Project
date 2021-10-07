package com.project.setech.model;

import java.util.List;

public abstract class Item implements IItem {
    private final String name;
    private final List<Integer> images;
    private final String price;

    public Item(String name, List<Integer> images, String price) {
        this.name = name;
        this.images = images;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public List<Integer> getImages() {
        return images;
    }
    public String getPrice() {
        return price;
    }
}
