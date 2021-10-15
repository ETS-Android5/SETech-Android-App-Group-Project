package com.project.setech.model;

import java.util.List;

public abstract class Item implements IItem {
    private final String id;
    private final String name;
    private final List<Integer> images;
    private final String price;
    private final String viewCount;

    public Item(String id, String name, List<Integer> images, String price, String viewCount) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.price = price;
        this.viewCount = viewCount;
    }

    public String getId() {
        return id;
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
    public String getViewCount() {
        return viewCount;
    }
}
